package upc.edu.pe.banca_upcina.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.banca_upcina.DTO.AuthoritiesDTO;
import upc.edu.pe.banca_upcina.Entities.Authorities;
import upc.edu.pe.banca_upcina.ServiceInterfaces.AuthoritiesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthoritiesService aS;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<AuthoritiesDTO> list() {
        return aS.list().stream()
                .map(a -> modelMapper.map(a, AuthoritiesDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Authorities> opt = aS.getAuthorityId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authority no encontrada");
        }
        return ResponseEntity.ok(modelMapper.map(opt.get(), AuthoritiesDTO.class));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody AuthoritiesDTO dto) {
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No envió datos");
        }
        Authorities a = modelMapper.map(dto, Authorities.class);
        Authorities saved = aS.insert(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(saved, AuthoritiesDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        Optional<Authorities> opt = aS.getAuthorityId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authority no encontrada");
        }
        aS.delete(id);
        return ResponseEntity.ok("Authority eliminada exitosamente");
    }
}
