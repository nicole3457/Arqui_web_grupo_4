package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.LeccionesDTO;
import upc.edu.pe.banca_upcina.Entities.Cursos;
import upc.edu.pe.banca_upcina.Entities.Lecciones;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CursosService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.InscripcionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.LeccionesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lecciones")
public class LeccionesController {

    @Autowired
    private LeccionesService lS;

    @Autowired
    private CursosService cS;

    @Autowired
    private InscripcionesService iS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<LeccionesDTO> list() {
        return lS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Lecciones> opt = lS.getLeccionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lección no encontrada");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    @GetMapping("/curso/{cursoId}")
    public List<LeccionesDTO> listByCurso(@PathVariable("cursoId") int cursoId) {
        return lS.findByCursoId(cursoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // US16: Reproducir una lección
    @GetMapping("/{id}/reproducir")
    public ResponseEntity<?> reproducirLeccion(
            @PathVariable("id") int id,
            @RequestParam("estudianteId") int estudianteId) {

        Optional<Lecciones> opt = lS.getLeccionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lección no encontrada");
        }

        Lecciones leccion = opt.get();
        int cursoId = leccion.getCursos() != null ? leccion.getCursos().getId() : 0;

        // Escenario 2: El estudiante no está inscrito en el curso
        boolean estaInscrito = iS.existsByEstudianteIdAndCursoId(estudianteId, cursoId);
        if (!estaInscrito) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Debe inscribirse al curso para poder acceder y reproducir sus lecciones");
        }

        // Escenario 1: El estudiante está inscrito -> el sistema reproduce el video
        Map<String, Object> reproduccion = new HashMap<>();
        reproduccion.put("leccionId", leccion.getId());
        reproduccion.put("titulo", leccion.getTitulo());
        reproduccion.put("duracionMinutos", leccion.getDuracionMinutos());
        reproduccion.put("enlaceVideo", leccion.getUrlVideo());
        reproduccion.put("cursoId", cursoId);
        reproduccion.put("mensaje", "Reproducción autorizada");

        return ResponseEntity.ok(reproduccion);
    }

    // US11: Agregar lecciones a un curso
    @PostMapping
    public ResponseEntity<?> post(@RequestBody LeccionesDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El título de la lección es obligatorio");
        }

        // Escenario 2: No se ingresa una duración válida (duración <= 0)
        if (dto.getDuracionMinutos() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La duración de la lección debe ser un número positivo mayor a 0 minutos");
        }

        Optional<Cursos> curOpt = cS.getCursoId(dto.getCursoId());
        if (curOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Curso");
        }

        // Escenario 1: Sube enlace, duración y orden y se asocia al curso
        Lecciones l = modelMapper.map(dto, Lecciones.class);
        l.setCursos(curOpt.get());

        Lecciones saved = lS.insert(l);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Lecciones> opt = lS.getLeccionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lección no encontrada");
        }
        lS.delete(id);
        return ResponseEntity.ok("Lección eliminada exitosamente");
    }

    private LeccionesDTO toDTO(Lecciones l) {
        LeccionesDTO dto = modelMapper.map(l, LeccionesDTO.class);
        if (l.getCursos() != null) {
            dto.setCursoId(l.getCursos().getId());
        }
        return dto;
    }
}
