package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Respuestas;

import java.util.List;

@Repository
public interface RespuestasRepository extends JpaRepository<Respuestas, Integer> {
    List<Respuestas> findByPreguntasId(int preguntaId);
}
