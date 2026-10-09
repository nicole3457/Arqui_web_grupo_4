package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.PreguntasDTO;
import upc.edu.pe.banca_upcina.Entities.Authorities;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.Entities.Preguntas;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.PreguntasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/preguntas")
public class PreguntasController {

    @Autowired
    private PreguntasService pS;

    @Autowired
    private UsersService uS;

    @Autowired
    private CategoriasService cS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<PreguntasDTO> list() {
        return pS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Preguntas> opt = pS.getPreguntaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    // US23: Filtrar preguntas por categoría financiera
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> getByCategoria(@PathVariable("categoriaId") int categoriaId) {
        Optional<Categorias> catOpt = cS.getCategoriaId(categoriaId);
        if (catOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Categoría");
        }

        List<PreguntasDTO> list = pS.findByCategoriaId(categoriaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        // Escenario 1 y 2: Retorna lista de preguntas o lista vacía si no hay resultados
        return ResponseEntity.ok(list);
    }

    // US21: Publicar una pregunta
    @PostMapping({"", "/post"})
    public ResponseEntity<?> post(@RequestBody PreguntasDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        // Escenario 2: Validar que el título y contenido no estén vacíos
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty() ||
                dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El título y el contenido de la pregunta son obligatorios");
        }

        int userId = dto.getUsuarioId() != 0 ? dto.getUsuarioId() : dto.getUsuario_id();
        int catId = dto.getCategoriaId() != 0 ? dto.getCategoriaId() : dto.getCategorias_id();

        Optional<Users> userOpt = uS.getUserId(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Usuario");
        }

        Optional<Categorias> catOpt = cS.getCategoriaId(catId);
        if (catOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Categoría");
        }

        // Escenario 1: El usuario ingresa título, contenido y categoría válidos -> crea la pregunta
        Preguntas p = modelMapper.map(dto, Preguntas.class);
        p.setTitulo(dto.getTitulo().trim());
        p.setContenido(dto.getContenido().trim());
        p.setUsers(userOpt.get());
        p.setCategorias(catOpt.get());
        p.setFechaCreacion(LocalDateTime.now());

        Preguntas saved = pS.insert(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // US25: Moderar contenido del foro (Eliminar preguntas con control de permisos)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") int id,
            @RequestParam(value = "userId", required = false) Integer userId) {

        Optional<Preguntas> opt = pS.getPreguntaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada");
        }

        Preguntas pregunta = opt.get();

        // Escenario 2: Si se envía userId, verificar si es el autor o Administrador
        if (userId != null) {
            Optional<Users> userOpt = uS.getUserId(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario moderador no encontrado");
            }

            Users requester = userOpt.get();
            boolean esAdmin = requester.getAuthorities() != null &&
                    requester.getAuthorities().stream()
                            .map(Authorities::getName)
                            .anyMatch(name -> name.equalsIgnoreCase("ADMIN") || name.equalsIgnoreCase("ROLE_ADMIN"));

            boolean esAutor = pregunta.getUsers() != null && pregunta.getUsers().getId() == userId;

            if (!esAdmin && !esAutor) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tiene permisos para eliminar esta pregunta del foro");
            }
        }

        // Escenario 1: El sistema la retira del foro de forma permanente
        pS.delete(id);
        return ResponseEntity.ok("Pregunta eliminada correctamente del foro");
    }

    private PreguntasDTO toDTO(Preguntas p) {
        PreguntasDTO dto = modelMapper.map(p, PreguntasDTO.class);
        if (p.getUsers() != null) {
            dto.setUsuario_id(p.getUsers().getId());
            dto.setUsuarioId(p.getUsers().getId());
        }
        if (p.getCategorias() != null) {
            dto.setCategorias_id(p.getCategorias().getId());
            dto.setCategoriaId(p.getCategorias().getId());
        }
        return dto;
    }
}
