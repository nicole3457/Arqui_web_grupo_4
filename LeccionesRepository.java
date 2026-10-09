package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Lecciones;

import java.util.List;

@Repository
public interface LeccionesRepository extends JpaRepository<Lecciones, Integer> {
    List<Lecciones> findByCursosId(int cursoId);
    List<Lecciones> findByCursosIdOrderByOrdenAsc(int cursoId);
    long countByCursosId(int cursoId);
}
