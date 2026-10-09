package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Cursos;
import upc.edu.pe.banca_upcina.Repositories.CursosRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CursosService;

import java.util.List;
import java.util.Optional;

@Service
public class CursosServiceImplement implements CursosService {

    @Autowired
    private CursosRepository cR;

    @Override
    public List<Cursos> list() {
        return cR.findAll();
    }

    @Override
    public Cursos insert(Cursos cursos) {
        return cR.save(cursos);
    }

    @Override
    public Optional<Cursos> getCursoId(int id) {
        return cR.findById(id);
    }

    @Override
    public List<Cursos> findByCategoriaId(int categoriaId) {
        return cR.findByCategoriasId(categoriaId);
    }

    @Override
    public List<Cursos> findByEstado(String estado) {
        return cR.findByEstadoIgnoreCase(estado);
    }

    @Override
    public List<Cursos> findCatalogo(Integer categoriaId, String nivel) {
        if (categoriaId != null && nivel != null && !nivel.isBlank()) {
            return cR.findByEstadoIgnoreCaseAndCategoriasIdAndNivelIgnoreCase("publicado", categoriaId, nivel);
        } else if (categoriaId != null) {
            return cR.findByEstadoIgnoreCaseAndCategoriasId("publicado", categoriaId);
        } else if (nivel != null && !nivel.isBlank()) {
            return cR.findByEstadoIgnoreCaseAndNivelIgnoreCase("publicado", nivel);
        } else {
            return cR.findByEstadoIgnoreCase("publicado");
        }
    }

    @Override
    public Cursos update(Cursos cursos) {
        return cR.save(cursos);
    }

    @Override
    public void delete(int id) {
        cR.deleteById(id);
    }
}
