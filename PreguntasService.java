package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Preguntas;

import java.util.List;
import java.util.Optional;

public interface PreguntasService {
    List<Preguntas> list();
    Preguntas insert(Preguntas preguntas);
    Optional<Preguntas> getPreguntaId(int id);
    List<Preguntas> findByCategoriaId(int categoriaId);
    Preguntas update(Preguntas preguntas);
    void delete(int id);
}
