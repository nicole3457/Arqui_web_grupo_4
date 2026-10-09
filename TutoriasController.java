package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.CalificarTutoriaDTO;
import upc.edu.pe.banca_upcina.DTO.TutoriasDTO;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.Entities.Tutorias;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.EstudiantesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoriasService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tutorias")
public class TutoriasController {

    @Autowired
    private TutoriasService tuS;

    @Autowired
    private EstudiantesService eS;

    @Autowired
    private TutoresService tS;

    @Autowired
    private CategoriasService cS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TutoriasDTO> list() {
        return tuS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Tutorias> opt = tuS.getTutoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutoría no encontrada");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<TutoriasDTO> listByEstudiante(@PathVariable("estudianteId") int estudianteId) {
        return tuS.findByEstudianteId(estudianteId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/tutor/{tutorId}")
    public List<TutoriasDTO> listByTutor(@PathVariable("tutorId") int tutorId) {
        return tuS.findByTutorId(tutorId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // US28: Agendar una tutoría
    @PostMapping
    public ResponseEntity<?> post(@RequestBody TutoriasDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        if (dto.getFechaHora() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha y hora de la tutoría son obligatorias");
        }

        Optional<Estudiantes> estOpt = eS.getEstudianteId(dto.getEstudianteId());
        if (estOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Estudiante");
        }

        Optional<Tutores> tutOpt = tS.getTutorId(dto.getTutorId());
        if (tutOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Tutor");
        }

        Tutores tutor = tutOpt.get();

        // Validar que el tutor esté verificado
        if (!"verificado".equalsIgnoreCase(tutor.getEstadoVerificacion())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tutor aún no se encuentra verificado para ofrecer tutorías");
        }

        // Escenario 2: Validar si la fecha y hora chocan con otra tutoría programada
        if (tuS.existsConflict(dto.getTutorId(), dto.getFechaHora())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El tutor ya tiene otra tutoría programada en ese horario");
        }

        Optional<Categorias> catOpt = cS.getCategoriaId(dto.getCategoriaId());
        if (catOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Categoría");
        }

        // Escenario 1: El sistema crea la tutoría en estado "programada"
        Tutorias t = modelMapper.map(dto, Tutorias.class);
        t.setEstudiantes(estOpt.get());
        t.setTutores(tutor);
        t.setCategorias(catOpt.get());
        t.setEstado("programada");

        if (t.getDuracionMinutos() <= 0) {
            t.setDuracionMinutos(60); // Duración estándar de 60 minutos si no se especifica
        }

        Tutorias saved = tuS.insert(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // US29: Gestionar el estado de una tutoría ("completada" o "cancelada")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable("id") int id,
            @RequestParam("estado") String estado) {

        Optional<Tutorias> opt = tuS.getTutoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutoría no encontrada");
        }

        String nuevoEstado = estado != null ? estado.trim().toLowerCase() : "";
        if (!nuevoEstado.equals("completada") && !nuevoEstado.equals("cancelada") && !nuevoEstado.equals("programada")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El estado debe ser 'completada' o 'cancelada'");
        }

        Tutorias tutoria = opt.get();
        // Escenario 1: Marca como "completada" -> habilita calificarla
        // Escenario 2: Marca como "cancelada" -> libera el horario
        tutoria.setEstado(nuevoEstado);
        Tutorias updated = tuS.update(tutoria);

        return ResponseEntity.ok(toDTO(updated));
    }

    // US30: Calificar una tutoría
    @PostMapping("/{id}/calificar")
    public ResponseEntity<?> calificarTutoria(
            @PathVariable("id") int id,
            @RequestBody CalificarTutoriaDTO dto) {

        Optional<Tutorias> opt = tuS.getTutoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutoría no encontrada");
        }

        Tutorias tutoria = opt.get();

        // Escenario 2: La tutoría aún no está en estado "completada"
        if (!"completada".equalsIgnoreCase(tutoria.getEstado())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solo se pueden calificar tutorías que se encuentren en estado 'completada'");
        }

        if (dto == null || dto.getCalificacion() < 1 || dto.getCalificacion() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La calificación debe ser un número entero entre 1 y 5");
        }

        // Escenario 1: Registra la calificación y comentario
        tutoria.setCalificacion(dto.getCalificacion());
        tutoria.setComentario(dto.getComentario() != null ? dto.getComentario().trim() : null);

        Tutorias updated = tuS.update(tutoria);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Tutorias> opt = tuS.getTutoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutoría no encontrada");
        }
        tuS.delete(id);
        return ResponseEntity.ok("Tutoría eliminada exitosamente");
    }

    private TutoriasDTO toDTO(Tutorias t) {
        TutoriasDTO dto = modelMapper.map(t, TutoriasDTO.class);
        if (t.getEstudiantes() != null) dto.setEstudianteId(t.getEstudiantes().getId());
        if (t.getTutores() != null) dto.setTutorId(t.getTutores().getId());
        if (t.getCategorias() != null) dto.setCategoriaId(t.getCategorias().getId());
        return dto;
    }
}
