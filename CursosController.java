package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.CursoDetalleDTO;
import upc.edu.pe.banca_upcina.DTO.CursosDTO;
import upc.edu.pe.banca_upcina.DTO.LeccionesDTO;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.Entities.Cursos;
import upc.edu.pe.banca_upcina.Entities.Lecciones;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CursosService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.InscripcionesService;
import upc.edu.pe.banca_upcina.ServiceInterfaces.LeccionesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursosController {

    @Autowired
    private CursosService cS;

    @Autowired
    private CategoriasService catS;

    @Autowired
    private LeccionesService lS;

    @Autowired
    private InscripcionesService iS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CursosDTO> list() {
        return cS.list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // US13: Explorar catálogo de cursos (solo publicados, filtrando por categoría y nivel)
    @GetMapping("/catalogo")
    public ResponseEntity<?> catalogo(
            @RequestParam(value = "categoriaId", required = false) Integer categoriaId,
            @RequestParam(value = "nivel", required = false) String nivel) {

        List<CursosDTO> list = cS.findCatalogo(categoriaId, nivel).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        // Escenario 1 y 2: Retorna lista de cursos publicados o lista vacía si no hay resultados
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Cursos> opt = cS.getCursoId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        return ResponseEntity.ok(toDTO(opt.get()));
    }

    // US14: Ver detalle de un curso (lecciones, duración y nivel)
    @GetMapping("/{id}/detalle")
    public ResponseEntity<?> getDetalle(
            @PathVariable("id") int id,
            @RequestParam(value = "estudianteId", required = false) Integer estudianteId) {

        Optional<Cursos> opt = cS.getCursoId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }

        Cursos curso = opt.get();

        // Escenario 2: Dado que el curso está archivado y el estudiante no está inscrito
        if ("archivado".equalsIgnoreCase(curso.getEstado())) {
            boolean inscrito = estudianteId != null && iS.existsByEstudianteIdAndCursoId(estudianteId, id);
            if (!inscrito) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acceso restringido: El curso se encuentra archivado y no estás inscrito en él");
            }
        }

        // Escenario 1: Mostrar detalle del curso con lista de lecciones y duración
        List<Lecciones> lecciones = lS.findByCursoId(id);
        List<LeccionesDTO> leccionesDTO = lecciones.stream().map(l -> {
            LeccionesDTO dto = modelMapper.map(l, LeccionesDTO.class);
            if (l.getCursos() != null) dto.setCursoId(l.getCursos().getId());
            return dto;
        }).collect(Collectors.toList());

        int duracionTotal = lecciones.stream().mapToInt(Lecciones::getDuracionMinutos).sum();

        CursoDetalleDTO detalle = new CursoDetalleDTO();
        detalle.setId(curso.getId());
        detalle.setNombre(curso.getNombre());
        detalle.setDescripcion(curso.getDescripcion());
        detalle.setNivel(curso.getNivel());
        detalle.setEstado(curso.getEstado());
        if (curso.getCategorias() != null) {
            detalle.setCategoriaId(curso.getCategorias().getId());
            detalle.setCategoriaNombre(curso.getCategorias().getNombre());
        }
        detalle.setLecciones(leccionesDTO);
        detalle.setDuracionTotalMinutos(duracionTotal);

        return ResponseEntity.ok(detalle);
    }

    // US10: Crear curso
    @PostMapping
    public ResponseEntity<?> post(@RequestBody CursosDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre del curso es obligatorio");
        }

        // Escenario 2: Administrador no selecciona categoría válida
        Optional<Categorias> catOpt = catS.getCategoriaId(dto.getCategoriaId());
        if (catOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Debe seleccionar una categoría válida");
        }

        Cursos c = modelMapper.map(dto, Cursos.class);
        c.setCategorias(catOpt.get());

        // Escenario 1: El sistema crea el curso en estado "borrador"
        if (c.getEstado() == null || c.getEstado().isBlank()) {
            c.setEstado("borrador");
        }

        Cursos saved = cS.insert(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // US12: Publicar un curso
    @PatchMapping("/{id}/publicar")
    public ResponseEntity<?> publicarCurso(@PathVariable("id") int id) {
        Optional<Cursos> opt = cS.getCursoId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }

        Cursos curso = opt.get();
        long cantLecciones = lS.countByCursoId(id);

        // Escenario 2: Si el curso no tiene ninguna lección, impide la publicación
        if (cantLecciones == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede publicar el curso porque no tiene ninguna lección registrada");
        }

        // Escenario 1: El curso tiene al menos una lección -> estado cambia a "publicado"
        curso.setEstado("publicado");
        Cursos updated = cS.update(curso);
        return ResponseEntity.ok(toDTO(updated));
    }

    // US20: Archivar un curso
    @PatchMapping("/{id}/archivar")
    public ResponseEntity<?> archivarCurso(@PathVariable("id") int id) {
        Optional<Cursos> opt = cS.getCursoId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }

        // Escenario 1: Cambia el estado a "archivado"
        Cursos curso = opt.get();
        curso.setEstado("archivado");
        Cursos updated = cS.update(curso);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Cursos> opt = cS.getCursoId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        cS.delete(id);
        return ResponseEntity.ok("Curso eliminado exitosamente");
    }

    private CursosDTO toDTO(Cursos c) {
        CursosDTO dto = modelMapper.map(c, CursosDTO.class);
        if (c.getCategorias() != null) {
            dto.setCategoriaId(c.getCategorias().getId());
        }
        return dto;
    }
}
