package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.CategoriasDTO;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {

    @Autowired
    private CategoriasService cS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CategoriasDTO> list() {
        return cS.list().stream()
                .map(c -> modelMapper.map(c, CategoriasDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Categorias> opt = cS.getCategoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
        }
        return ResponseEntity.ok(modelMapper.map(opt.get(), CategoriasDTO.class));
    }

    // US09: Crear categoría financiera
    @PostMapping
    public ResponseEntity<?> post(@RequestBody CategoriasDTO dto) {
        if (dto == null || dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de la categoría es obligatorio");
        }

        // Escenario 2: El nombre de la categoría ya existe
        if (cS.existsByNombre(dto.getNombre().trim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de la categoría ya existe");
        }

        // Escenario 1: El administrador ingresa un nombre único y guarda el registro
        Categorias c = modelMapper.map(dto, Categorias.class);
        c.setNombre(dto.getNombre().trim());
        Categorias saved = cS.insert(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(saved, CategoriasDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Categorias> opt = cS.getCategoriaId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
        }
        cS.delete(id);
        return ResponseEntity.ok("Categoría eliminada exitosamente");
    }
}
