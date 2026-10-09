package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Respuestas;
import upc.edu.pe.banca_upcina.Repositories.RespuestasRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.RespuestasService;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestasServiceImplement implements RespuestasService {

    @Autowired
    private RespuestasRepository rR;

    @Override
    public List<Respuestas> list() {
        return rR.findAll();
    }

    @Override
    public Respuestas insert(Respuestas respuestas) {
        return rR.save(respuestas);
    }

    @Override
    public Optional<Respuestas> getRespuestaId(int id) {
        return rR.findById(id);
    }

    @Override
    public List<Respuestas> findByPreguntaId(int preguntaId) {
        return rR.findByPreguntasId(preguntaId);
    }

    @Override
    public Respuestas update(Respuestas respuestas) {
        return rR.save(respuestas);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }
}
