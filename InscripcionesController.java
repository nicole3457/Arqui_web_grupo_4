package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.InscripcionesDTO;
import upc.edu.pe.banca_upcina.DTO.ProgresoCursoDTO;
import upc.edu.pe.banca_upcina.Entities.Cursos;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;
import upc.edu.pe.banca_upcina.Entities.Inscripciones;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CursosService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.EstudiantesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.InscripcionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.LeccionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.ProgresoLeccionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionesController {

    @Autowired
    private InscripcionesService iS;

    @Autowired
    private EstudiantesService eS;

    @Autowired
    private CursosService cS;

    @Autowired
    private LeccionesService lS;

    @Autowired
    private ProgresoLeccionService pLS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<InscripcionesDTO> list() {
        return iS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Inscripciones> opt = iS.getInscripcionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inscripción no encontrada");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    // US18: Visualizar progreso de un curso (Porcentaje de avance, lecciones completadas vs total)
    @GetMapping("/{id}/progreso")
    public ResponseEntity<?> getProgreso(@PathVariable("id") int id) {
        Optional<Inscripciones> opt = iS.getInscripcionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inscripción no encontrada");
        }

        Inscripciones ins = opt.get();
        int cursoId = ins.getCursos() != null ? ins.getCursos().getId() : 0;
        String cursoNombre = ins.getCursos() != null ? ins.getCursos().getNombre() : "";
        int estudianteId = ins.getEstudiantes() != null ? ins.getEstudiantes().getId() : 0;

        int totalLecciones = (int) lS.countByCursoId(cursoId);
        int leccionesCompletadas = (int) pLS.countCompletadas(id);

        // Escenario 1 y 2: Calcula porcentaje (0% si aún no completa lecciones)
        double porcentaje = totalLecciones > 0 ?
                Math.round(((double) leccionesCompletadas / totalLecciones) * 10000.0) / 100.0 :
                0.0;

        String estadoCurso = ins.getFechaCompletado() != null ? "completado" : "en progreso";

        ProgresoCursoDTO dto = new ProgresoCursoDTO(
                ins.getId(),
                estudianteId,
                cursoId,
                cursoNombre,
                totalLecciones,
                leccionesCompletadas,
                porcentaje,
                estadoCurso,
                ins.getFechaCompletado()
        );

        return ResponseEntity.ok(dto);
    }

    // US15: Inscribirse a un curso
    @PostMapping
    public ResponseEntity<?> post(@RequestBody InscripcionesDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        Optional<Estudiantes> estOpt = eS.getEstudianteId(dto.getEstudianteId());
        if (estOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Estudiante");
        }

        Optional<Cursos> curOpt = cS.getCursoId(dto.getCursoId());
        if (curOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Curso");
        }

        Cursos curso = curOpt.get();

        // Validar que el curso esté publicado
        if (!"publicado".equalsIgnoreCase(curso.getEstado())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No es posible inscribirse a un curso que no esté publicado");
        }

        // Escenario 2: El estudiante ya está inscrito en ese curso
        if (iS.existsByEstudianteIdAndCursoId(dto.getEstudianteId(), dto.getCursoId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El estudiante ya está inscrito en este curso");
        }

        // Escenario 1: El estudiante no está inscrito -> crea el registro con fecha_inscripcion
        Inscripciones i = modelMapper.map(dto, Inscripciones.class);
        i.setEstudiantes(estOpt.get());
        i.setCursos(curso);
        i.setFechaInscripcion(LocalDate.now());

        Inscripciones saved = iS.insert(i);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Inscripciones> opt = iS.getInscripcionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inscripción no encontrada");
        }
        iS.delete(id);
        return ResponseEntity.ok("Inscripción eliminada exitosamente");
    }

    private InscripcionesDTO toDTO(Inscripciones i) {
        InscripcionesDTO dto = modelMapper.map(i, InscripcionesDTO.class);
        if (i.getEstudiantes() != null) dto.setEstudianteId(i.getEstudiantes().getId());
        if (i.getCursos() != null) dto.setCursoId(i.getCursos().getId());
        return dto;
    }
}
