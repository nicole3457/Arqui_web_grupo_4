package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.TutoresDTO;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tutores")
public class TutoresController {

    @Autowired
    private TutoresService tS;

    @Autowired
    private UsersService uS;

    @Autowired
    private CategoriasService cS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TutoresDTO> list() {
        return tS.list().stream().map(t -> toDTO(t)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Tutores> opt = tS.getTutorId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor no encontrado");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    // US26: Buscar tutores verificados por categoría financiera
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> getByCategoria(@PathVariable("categoriaId") int categoriaId) {
        Optional<Categorias> catOpt = cS.getCategoriaId(categoriaId);
        if (catOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la categoría especificada");
        }

        List<TutoresDTO> tutores = tS.findVerificadosByCategoriaId(categoriaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        // Escenario 2: Si no hay tutores verificados en la categoría buscada
        if (tutores.isEmpty()) {
            return ResponseEntity.ok(tutores);
        }

        // Escenario 1: Muestra lista de tutores disponibles
        return ResponseEntity.ok(tutores);
    }

    // US04: Registrar perfil de tutor
    @PostMapping
    public ResponseEntity<?> post(@RequestBody TutoresDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        Optional<Users> userOpt = uS.getUserId(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Usuario");
        }

        // Escenario 2: El usuario ya tiene un perfil de tutor registrado
        if (tS.existsByUserId(dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya tiene un perfil de tutor registrado");
        }

        Tutores t = modelMapper.map(dto, Tutores.class);
        t.setUsers(userOpt.get());

        // Escenario 1: Crear perfil con estado_verificacion en "pendiente"
        t.setEstadoVerificacion("pendiente");

        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            Set<Categorias> cats = new HashSet<>();
            for (Integer catId : dto.getCategoriaIds()) {
                cS.getCategoriaId(catId).ifPresent(cats::add);
            }
            t.setCategorias(cats);
        }

        Tutores saved = tS.insert(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // US05: Verificar perfil de tutor (Aprobar o rechazar)
    @PatchMapping("/{id}/verificar")
    public ResponseEntity<?> verificarTutor(@PathVariable("id") int id, @RequestParam("estado") String estado) {
        Optional<Tutores> opt = tS.getTutorId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor no encontrado");
        }

        String nuevoEstado = estado != null ? estado.trim().toLowerCase() : "";
        if (!nuevoEstado.equals("verificado") && !nuevoEstado.equals("rechazado") && !nuevoEstado.equals("pendiente")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El estado debe ser 'verificado' o 'rechazado'");
        }

        Tutores tutor = opt.get();
        // Escenario 1: El administrador aprueba -> estado cambia a "verificado"
        // Escenario 2: El administrador rechaza -> estado cambia a "rechazado"
        tutor.setEstadoVerificacion(nuevoEstado);
        Tutores updated = tS.update(tutor);

        return ResponseEntity.ok(toDTO(updated));
    }

    // US06: Asignar categorías de especialidad a un tutor
    @PutMapping("/{id}/categorias")
    public ResponseEntity<?> asignarCategorias(@PathVariable("id") int id, @RequestBody Set<Integer> categoriaIds) {
        Optional<Tutores> opt = tS.getTutorId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor no encontrado");
        }

        Tutores tutor = opt.get();

        // Escenario 1: Verificar que el tutor tenga su perfil verificado
        if (tutor.getEstadoVerificacion() == null || !tutor.getEstadoVerificacion().equalsIgnoreCase("verificado")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tutor debe tener su perfil verificado para asignarle categorías");
        }

        // Escenario 2: Si el tutor no selecciona ninguna categoría
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe seleccionar al menos una categoría");
        }

        Set<Categorias> cats = new HashSet<>();
        for (Integer catId : categoriaIds) {
            Optional<Categorias> catOpt = cS.getCategoriaId(catId);
            if (catOpt.isPresent()) {
                cats.add(catOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La categoría con ID " + catId + " no existe");
            }
        }

        tutor.setCategorias(cats);
        Tutores updated = tS.update(tutor);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Tutores> opt = tS.getTutorId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor no encontrado");
        }
        tS.delete(id);
        return ResponseEntity.ok("Tutor eliminado exitosamente");
    }

    private TutoresDTO toDTO(Tutores t) {
        TutoresDTO dto = modelMapper.map(t, TutoresDTO.class);
        if (t.getUsers() != null) {
            dto.setUserId(t.getUsers().getId());
        }
        if (t.getCategorias() != null) {
            dto.setCategoriaIds(t.getCategorias().stream().map(Categorias::getId).collect(Collectors.toSet()));
        }
        return dto;
    }
}
