package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.LoginRequestDTO;
import upc.edu.pe.banca_upcina.DTO.LoginResponseDTO;
import upc.edu.pe.banca_upcina.DTO.RegisterRequestDTO;
import upc.edu.pe.banca_upcina.DTO.UsersDTO;
import upc.edu.pe.banca_upcina.Entities.Authorities;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.ServiceInterfaces.AuthoritiesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.EstudiantesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService uS;

    @Autowired
    private AuthoritiesService aS;

    @Autowired
    private EstudiantesService eS;

    @Autowired
    private TutoresService tS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UsersDTO> list() {
        return uS.list().stream().map(u -> {
            UsersDTO dto = modelMapper.map(u, UsersDTO.class);
            if (u.getAuthorities() != null) {
                dto.setAuthorityIds(u.getAuthorities().stream().map(Authorities::getId).collect(Collectors.toSet()));
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Users> opt = uS.getUserId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        Users u = opt.get();
        UsersDTO dto = modelMapper.map(u, UsersDTO.class);
        if (u.getAuthorities() != null) {
            dto.setAuthorityIds(u.getAuthorities().stream().map(Authorities::getId).collect(Collectors.toSet()));
        }
        return ResponseEntity.ok(dto);
    }

    // US01: Registrar usuario (Como estudiante)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        if (dto == null || dto.getUsername() == null || dto.getUsername().isBlank() ||
                dto.getPassword() == null || dto.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario y la contraseña son obligatorios");
        }

        // Escenario 2: Validar si el username ya existe
        if (uS.existsByUsername(dto.getUsername().trim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
        }

        // Validar ciclo académico
        if (dto.getCiclo() < 1 || dto.getCiclo() > 12) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ciclo académico debe ser un valor válido entre 1 y 12");
        }

        // Crear usuario
        Users user = new Users();
        user.setUsername(dto.getUsername().trim());
        user.setPassword(dto.getPassword().trim());

        // Escenario 1: Asignar rol ESTUDIANTE
        Authorities estudianteAuth = aS.findByName("ESTUDIANTE").orElseGet(() -> {
            Authorities newAuth = new Authorities();
            newAuth.setName("ESTUDIANTE");
            return aS.insert(newAuth);
        });

        Set<Authorities> auths = new HashSet<>();
        auths.add(estudianteAuth);
        user.setAuthorities(auths);

        Users savedUser = uS.insert(user);

        // Escenario 1: Crear perfil en estudiantes
        Estudiantes estudiante = new Estudiantes();
        estudiante.setUsers(savedUser);
        estudiante.setUniversidad(dto.getUniversidad() != null ? dto.getUniversidad().trim() : "UPC");
        estudiante.setCarrera(dto.getCarrera() != null ? dto.getCarrera().trim() : "Ingeniería");
        estudiante.setCiclo(dto.getCiclo());
        estudiante.setNombre(savedUser.getUsername());
        estudiante.setCorreo(savedUser.getUsername() + "@upc.edu.pe");
        eS.insert(estudiante);

        UsersDTO resultDTO = modelMapper.map(savedUser, UsersDTO.class);
        resultDTO.setAuthorityIds(savedUser.getAuthorities().stream().map(Authorities::getId).collect(Collectors.toSet()));
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDTO);
    }

    // US02: Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        if (dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingrese usuario y contraseña");
        }

        Optional<Users> userOpt = uS.findByUsername(dto.getUsername().trim());
        // Escenario 2: Credenciales incorrectas
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(dto.getPassword().trim())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        Users user = userOpt.get();
        Set<String> roleNames = user.getAuthorities() != null ?
                user.getAuthorities().stream().map(Authorities::getName).collect(Collectors.toSet()) :
                new HashSet<>();

        Integer estudianteId = eS.findByUserId(user.getId()).map(Estudiantes::getId).orElse(null);
        Integer tutorId = tS.findByUserId(user.getId()).map(Tutores::getId).orElse(null);

        // Escenario 1: Permitir acceso según rol
        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                roleNames,
                estudianteId,
                tutorId,
                "Inicio de sesión exitoso"
        );
        return ResponseEntity.ok(response);
    }

    // US08: Gestionar roles de usuario (Asignar o modificar roles)
    @PutMapping("/{id}/roles")
    public ResponseEntity<?> updateRoles(@PathVariable("id") int id, @RequestBody Set<Integer> authorityIds) {
        Optional<Users> userOpt = uS.getUserId(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        if (authorityIds == null || authorityIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe especificar al menos un rol");
        }

        Set<Authorities> updatedAuths = new HashSet<>();
        for (Integer authId : authorityIds) {
            Optional<Authorities> authOpt = aS.getAuthorityId(authId);
            // Escenario 2: Si se intenta asignar un rol inexistente
            if (authOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El rol con ID " + authId + " no existe");
            }
            updatedAuths.add(authOpt.get());
        }

        Users user = userOpt.get();
        user.setAuthorities(updatedAuths);
        Users saved = uS.update(user);

        UsersDTO dto = modelMapper.map(saved, UsersDTO.class);
        dto.setAuthorityIds(saved.getAuthorities().stream().map(Authorities::getId).collect(Collectors.toSet()));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UsersDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }
        if (dto.getUsername() != null && uS.existsByUsername(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
        }
        Users u = modelMapper.map(dto, Users.class);
        if (dto.getAuthorityIds() != null && !dto.getAuthorityIds().isEmpty()) {
            Set<Authorities> auths = new HashSet<>();
            for (Integer authId : dto.getAuthorityIds()) {
                Optional<Authorities> aOpt = aS.getAuthorityId(authId);
                if (aOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El rol con ID " + authId + " no existe");
                }
                auths.add(aOpt.get());
            }
            u.setAuthorities(auths);
        }
        Users saved = uS.insert(u);
        UsersDTO savedDTO = modelMapper.map(saved, UsersDTO.class);
        if (saved.getAuthorities() != null) {
            savedDTO.setAuthorityIds(saved.getAuthorities().stream().map(Authorities::getId).collect(Collectors.toSet()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Users> opt = uS.getUserId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        uS.delete(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }
}
