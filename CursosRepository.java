package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Cursos;

import java.util.List;

@Repository
public interface CursosRepository extends JpaRepository<Cursos, Integer> {
    List<Cursos> findByCategoriasId(int categoriaId);
    List<Cursos> findByEstadoIgnoreCase(String estado);
    List<Cursos> findByEstadoIgnoreCaseAndCategoriasId(String estado, int categoriaId);
    List<Cursos> findByEstadoIgnoreCaseAndNivelIgnoreCase(String estado, String nivel);
    List<Cursos> findByEstadoIgnoreCaseAndCategoriasIdAndNivelIgnoreCase(String estado, int categoriaId, String nivel);
}
