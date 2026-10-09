package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Lecciones;
import upc.edu.pe.banca_upcina.Repositories.LeccionesRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.LeccionesService;

import java.util.List;
import java.util.Optional;

@Service
public class LeccionesServiceImplement implements LeccionesService {

    @Autowired
    private LeccionesRepository lR;

    @Override
    public List<Lecciones> list() {
        return lR.findAll();
    }

    @Override
    public Lecciones insert(Lecciones lecciones) {
        return lR.save(lecciones);
    }

    @Override
    public Optional<Lecciones> getLeccionId(int id) {
        return lR.findById(id);
    }

    @Override
    public List<Lecciones> findByCursoId(int cursoId) {
        return lR.findByCursosIdOrderByOrdenAsc(cursoId);
    }

    @Override
    public long countByCursoId(int cursoId) {
        return lR.countByCursosId(cursoId);
    }

    @Override
    public Lecciones update(Lecciones lecciones) {
        return lR.save(lecciones);
    }

    @Override
    public void delete(int id) {
        lR.deleteById(id);
    }
}
