package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.ProgresoLeccionDTO;
import upc.edu.pe.banca_upcina.Entities.Inscripciones;
import upc.edu.pe.banca_upcina.Entities.Lecciones;
import upc.edu.pe.banca_upcina.Entities.ProgresoLeccion;
import upc.edu.pe.banca_upcina.ServiceInterfaces.InscripcionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.LeccionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.ProgresoLeccionService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progreso-leccion")
public class ProgresoLeccionController {

    @Autowired
    private ProgresoLeccionService pLS;

    @Autowired
    private InscripcionesService iS;

    @Autowired
    private LeccionesService lS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProgresoLeccionDTO> list() {
        return pLS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<ProgresoLeccion> opt = pLS.getProgresoLeccionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso no encontrado");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    @GetMapping("/inscripcion/{inscripcionId}")
    public List<ProgresoLeccionDTO> listByInscripcion(@PathVariable("inscripcionId") int inscripcionId) {
        return pLS.findByInscripcionId(inscripcionId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // US17: Marcar lección como completada
    // US19: Completar un curso (si completa todas las lecciones, actualiza fecha_completado en inscripción)
    @PostMapping("/completar")
    public ResponseEntity<?> completarLeccion(
            @RequestParam("inscripcionId") int inscripcionId,
            @RequestParam("leccionId") int leccionId) {

        Optional<Inscripciones> insOpt = iS.getInscripcionId(inscripcionId);
        if (insOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inscripción no encontrada");
        }

        Optional<Lecciones> lecOpt = lS.getLeccionId(leccionId);
        if (lecOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lección no encontrada");
        }

        Inscripciones inscripcion = insOpt.get();
        Lecciones leccion = lecOpt.get();

        // Validar que la lección pertenezca al curso de la inscripción
        if (leccion.getCursos() == null || inscripcion.getCursos() == null ||
                leccion.getCursos().getId() != inscripcion.getCursos().getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La lección no pertenece al curso inscrito");
        }

        // US17 Escenario 1: Registrar completado en true y fecha_completado en progreso_leccion
        Optional<ProgresoLeccion> existingProgreso = pLS.findByInscripcionIdAndLeccionId(inscripcionId, leccionId);
        ProgresoLeccion progreso;
        if (existingProgreso.isPresent()) {
            progreso = existingProgreso.get();
        } else {
            progreso = new ProgresoLeccion();
            progreso.setInscripciones(inscripcion);
            progreso.setLecciones(leccion);
        }
        progreso.setCompletado(true);
        progreso.setFechaCompletado(LocalDateTime.now());

        ProgresoLeccion savedProgreso = existingProgreso.isPresent() ? pLS.update(progreso) : pLS.insert(progreso);

        // US19: Completar un curso si se completan todas las lecciones del curso
        int cursoId = inscripcion.getCursos().getId();
        long totalLecciones = lS.countByCursoId(cursoId);
        long completadas = pLS.countCompletadas(inscripcionId);

        boolean cursoCompletado = false;
        if (completadas >= totalLecciones && totalLecciones > 0) {
            // Escenario 1 (US19): Registra fecha_completado en la inscripción
            if (inscripcion.getFechaCompletado() == null) {
                inscripcion.setFechaCompletado(LocalDateTime.now());
                iS.update(inscripcion);
            }
            cursoCompletado = true;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("progreso", toDTO(savedProgreso));
        response.put("leccionesCompletadas", completadas);
        response.put("totalLecciones", totalLecciones);
        response.put("cursoCompletado", cursoCompletado);
        response.put("fechaCompletadoCurso", inscripcion.getFechaCompletado());
        response.put("mensaje", cursoCompletado ? "¡Felicitaciones! Has completado el curso." : "Lección marcada como completada.");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProgresoLeccionDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }
        Optional<Inscripciones> insOpt = iS.getInscripcionId(dto.getInscripcionId());
        if (insOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Inscripción");
        }
        Optional<Lecciones> lecOpt = lS.getLeccionId(dto.getLeccionId());
        if (lecOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Lección");
        }

        ProgresoLeccion p = modelMapper.map(dto, ProgresoLeccion.class);
        p.setInscripciones(insOpt.get());
        p.setLecciones(lecOpt.get());
        if (p.isCompletado() && p.getFechaCompletado() == null) {
            p.setFechaCompletado(LocalDateTime.now());
        }

        ProgresoLeccion saved = pLS.insert(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<ProgresoLeccion> opt = pLS.getProgresoLeccionId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso no encontrado");
        }
        pLS.delete(id);
        return ResponseEntity.ok("Progreso eliminado exitosamente");
    }

    private ProgresoLeccionDTO toDTO(ProgresoLeccion p) {
        ProgresoLeccionDTO dto = modelMapper.map(p, ProgresoLeccionDTO.class);
        if (p.getInscripciones() != null) dto.setInscripcionId(p.getInscripciones().getId());
        if (p.getLecciones() != null) dto.setLeccionId(p.getLecciones().getId());
        return dto;
    }
}
