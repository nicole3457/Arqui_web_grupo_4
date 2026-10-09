package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.DisponibilidadDTO;
import upc.edu.pe.banca_upcina.Entities.Disponibilidad;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.ServiceInterfaces.DisponibilidadService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService dS;

    @Autowired
    private TutoresService tS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<DisponibilidadDTO> list() {
        return dS.list().stream().map(d -> {
            DisponibilidadDTO dto = modelMapper.map(d, DisponibilidadDTO.class);
            if (d.getTutores() != null) {
                dto.setTutorId(d.getTutores().getId());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Disponibilidad> opt = dS.getDisponibilidadId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disponibilidad no encontrada");
        }
        Disponibilidad d = opt.get();
        DisponibilidadDTO dto = modelMapper.map(d, DisponibilidadDTO.class);
        if (d.getTutores() != null) {
            dto.setTutorId(d.getTutores().getId());
        }
        return ResponseEntity.ok(dto);
    }

    // US27: Consultar disponibilidad de un tutor
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<?> listByTutor(@PathVariable("tutorId") int tutorId) {
        Optional<Tutores> tutorOpt = tS.getTutorId(tutorId);
        if (tutorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Tutor");
        }

        List<DisponibilidadDTO> list = dS.findByTutorId(tutorId).stream().map(d -> {
            DisponibilidadDTO dto = modelMapper.map(d, DisponibilidadDTO.class);
            if (d.getTutores() != null) {
                dto.setTutorId(d.getTutores().getId());
            }
            return dto;
        }).collect(Collectors.toList());

        // Escenario 1: Muestra los días y horas disponibles
        // Escenario 2: Si no ha registrado bloques, la lista retorna vacía
        return ResponseEntity.ok(list);
    }

    // US07: Configurar disponibilidad horaria
    @PostMapping
    public ResponseEntity<?> post(@RequestBody DisponibilidadDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        if (dto.getDiaSemana() == null || dto.getDiaSemana().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El día de la semana es obligatorio");
        }

        if (dto.getHoraInicio() == null || dto.getHoraFin() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las horas de inicio y fin son obligatorias");
        }

        // Escenario 2: La hora de fin debe ser posterior a la hora de inicio
        if (!dto.getHoraFin().isAfter(dto.getHoraInicio())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La hora de fin debe ser posterior a la hora de inicio");
        }

        Optional<Tutores> tutorOpt = tS.getTutorId(dto.getTutorId());
        if (tutorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Tutor");
        }

        // Escenario 1: Guarda el bloque en disponibilidad
        Disponibilidad d = modelMapper.map(dto, Disponibilidad.class);
        d.setTutores(tutorOpt.get());

        Disponibilidad saved = dS.insert(d);
        DisponibilidadDTO savedDTO = modelMapper.map(saved, DisponibilidadDTO.class);
        if (saved.getTutores() != null) {
            savedDTO.setTutorId(saved.getTutores().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Disponibilidad> opt = dS.getDisponibilidadId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disponibilidad no encontrada");
        }
        dS.delete(id);
        return ResponseEntity.ok("Disponibilidad eliminada exitosamente");
    }
}
