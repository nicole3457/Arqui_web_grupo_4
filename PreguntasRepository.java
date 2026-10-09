package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Preguntas;

import java.util.List;

@Repository
public interface PreguntasRepository extends JpaRepository<Preguntas, Integer> {
    List<Preguntas> findByCategoriasId(int categoriaId);
}
