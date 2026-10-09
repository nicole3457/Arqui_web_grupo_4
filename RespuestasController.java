package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.RespuestasDTO;
import upc.edu.pe.banca_upcina.Entities.Authorities;
import upc.edu.pe.banca_upcina.Entities.Preguntas;
import upc.edu.pe.banca_upcina.Entities.Respuestas;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.ServiceInterfaces.PreguntasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.RespuestasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestasController {

    @Autowired
    private RespuestasService rS;

    @Autowired
    private PreguntasService pS;

    @Autowired
    private UsersService uS;

    @Autowired
    private TutoresService tS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<RespuestasDTO> list() {
        return rS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Respuestas> opt = rS.getRespuestaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    @GetMapping("/pregunta/{preguntaId}")
    public List<RespuestasDTO> listByPregunta(@PathVariable("preguntaId") int preguntaId) {
        return rS.findByPreguntaId(preguntaId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // US22: Responder una pregunta
    @PostMapping({"", "/post"})
    public ResponseEntity<?> post(@RequestBody RespuestasDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }

        if (dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El contenido de la respuesta es obligatorio");
        }

        int userId = dto.getUsuarioId() != 0 ? dto.getUsuarioId() : dto.getUsers();
        int preguntaId = dto.getPreguntaId() != 0 ? dto.getPreguntaId() : dto.getPreguntas();

        Optional<Users> userOpt = uS.getUserId(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese Usuario");
        }

        Optional<Preguntas> preguntaOpt = pS.getPreguntaId(preguntaId);
        if (preguntaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe esa Pregunta");
        }

        Preguntas pregunta = preguntaOpt.get();

        // Escenario 2: El usuario intenta responder su propia pregunta
        if (pregunta.getUsers() != null && pregunta.getUsers().getId() == userId) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No puedes responder a tu propia pregunta");
        }

        // Escenario 1: Asocia la respuesta a la pregunta junto con la fecha de creación
        Respuestas r = modelMapper.map(dto, Respuestas.class);
        r.setContenido(dto.getContenido().trim());
        r.setUsers(userOpt.get());
        r.setPreguntas(pregunta);
        r.setFechaCreacion(LocalDateTime.now());

        Respuestas saved = rS.insert(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // US25: Moderar contenido del foro (Eliminar respuestas)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") int id,
            @RequestParam(value = "userId", required = false) Integer userId) {

        Optional<Respuestas> opt = rS.getRespuestaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada");
        }

        Respuestas respuesta = opt.get();

        // Escenario 2: Control de permisos
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

            boolean esAutor = respuesta.getUsers() != null && respuesta.getUsers().getId() == userId;

            if (!esAdmin && !esAutor) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tiene permisos para eliminar esta respuesta del foro");
            }
        }

        // Escenario 1: El sistema la retira del foro de forma permanente
        rS.delete(id);
        return ResponseEntity.ok("Respuesta eliminada correctamente del foro");
    }

    // US24: Identificar respuestas de tutores verificados (Etiqueta experto)
    private RespuestasDTO toDTO(Respuestas r) {
        RespuestasDTO dto = modelMapper.map(r, RespuestasDTO.class);
        if (r.getUsers() != null) {
            dto.setUsers(r.getUsers().getId());
            dto.setUsuarioId(r.getUsers().getId());

            // Escenario 1: Quien responde tiene rol TUTOR y perfil verificado -> esExperto = true
            // Escenario 2: Es estudiante o no verificado -> esExperto = false
            Optional<Tutores> tutorOpt = tS.findByUserId(r.getUsers().getId());
            if (tutorOpt.isPresent() && "verificado".equalsIgnoreCase(tutorOpt.get().getEstadoVerificacion())) {
                dto.setEsExperto(true);
            } else {
                dto.setEsExperto(false);
            }
        }
        if (r.getPreguntas() != null) {
            dto.setPreguntas(r.getPreguntas().getId());
            dto.setPreguntaId(r.getPreguntas().getId());
        }
        return dto;
    }
}
