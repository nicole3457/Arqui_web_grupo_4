package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Respuestas;

import java.util.List;
import java.util.Optional;

public interface RespuestasService {
    List<Respuestas> list();
    Respuestas insert(Respuestas respuestas);
    Optional<Respuestas> getRespuestaId(int id);
    List<Respuestas> findByPreguntaId(int preguntaId);
    Respuestas update(Respuestas respuestas);
    void delete(int id);
}
