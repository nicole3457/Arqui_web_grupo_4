package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.EstudiantesDTO;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.ServiceInterfaces.EstudiantesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudiantesController {

    @Autowired
    private EstudiantesService eS;

    @Autowired
    private UsersService uS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<EstudiantesDTO> list() {
        return eS.list().stream().map(e -> {
            EstudiantesDTO dto = modelMapper.map(e, EstudiantesDTO.class);
            if (e.getUsers() != null) {
                dto.setUserId(e.getUsers().getId());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Estudiantes> opt = eS.getEstudianteId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
        }
        Estudiantes e = opt.get();
        EstudiantesDTO dto = modelMapper.map(e, EstudiantesDTO.class);
        if (e.getUsers() != null) {
            dto.setUserId(e.getUsers().getId());
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") int userId) {
        Optional<Estudiantes> opt = eS.findByUserId(userId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil de estudiante no encontrado para este usuario");
        }
        Estudiantes e = opt.get();
        EstudiantesDTO dto = modelMapper.map(e, EstudiantesDTO.class);
        if (e.getUsers() != null) {
            dto.setUserId(e.getUsers().getId());
        }
        return ResponseEntity.ok(dto);
    }

    // US03: Completar perfil de estudiante (POST o PUT)
    @PostMapping
    public ResponseEntity<?> post(@RequestBody EstudiantesDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }
        // Escenario 2: Validación de ciclo
        if (dto.getCiclo() < 1 || dto.getCiclo() > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ciclo académico debe ser un valor válido entre 1 y 12");
        }
        if (dto.getUniversidad() == null || dto.getUniversidad().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La universidad es obligatoria");
        }
        if (dto.getCarrera() == null || dto.getCarrera().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La carrera es obligatoria");
        }

        Optional<Users> userOpt = uS.getUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Usuario");
        }

        Estudiantes e = modelMapper.map(dto, Estudiantes.class);
        e.setUsers(userOpt.get());

        Estudiantes saved = eS.insert(e);
        EstudiantesDTO savedDTO = modelMapper.map(saved, EstudiantesDTO.class);
        if (saved.getUsers() != null) {
            savedDTO.setUserId(saved.getUsers().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    // US03: Completar / Actualizar perfil de estudiante
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody EstudiantesDTO dto) {
        Optional<Estudiantes> opt = eS.getEstudianteId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
        }

        // Escenario 2: Validación de ciclo
        if (dto.getCiclo() < 1 || dto.getCiclo() > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ciclo académico debe ser un valor válido entre 1 y 12");
        }

        Estudiantes e = opt.get();
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) e.setNombre(dto.getNombre());
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) e.setApellido(dto.getApellido());
        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()) e.setCorreo(dto.getCorreo());
        if (dto.getUniversidad() != null && !dto.getUniversidad().isBlank()) e.setUniversidad(dto.getUniversidad());
        if (dto.getCarrera() != null && !dto.getCarrera().isBlank()) e.setCarrera(dto.getCarrera());
        e.setCiclo(dto.getCiclo());

        Estudiantes updated = eS.update(e);
        EstudiantesDTO updatedDTO = modelMapper.map(updated, EstudiantesDTO.class);
        if (updated.getUsers() != null) {
            updatedDTO.setUserId(updated.getUsers().getId());
        }
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Estudiantes> opt = eS.getEstudianteId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
        }
        eS.delete(id);
        return ResponseEntity.ok("Estudiante eliminado exitosamente");
    }
}
