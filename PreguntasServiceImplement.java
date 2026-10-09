package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Preguntas;
import upc.edu.pe.banca_upcina.Repositories.PreguntasRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.PreguntasService;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntasServiceImplement implements PreguntasService {

    @Autowired
    private PreguntasRepository pR;

    @Override
    public List<Preguntas> list() {
        return pR.findAll();
    }

    @Override
    public Preguntas insert(Preguntas preguntas) {
        return pR.save(preguntas);
    }

    @Override
    public Optional<Preguntas> getPreguntaId(int id) {
        return pR.findById(id);
    }

    @Override
    public List<Preguntas> findByCategoriaId(int categoriaId) {
        return pR.findByCategoriasId(categoriaId);
    }

    @Override
    public Preguntas update(Preguntas preguntas) {
        return pR.save(preguntas);
    }

    @Override
    public void delete(int id) {
        pR.deleteById(id);
    }
}
