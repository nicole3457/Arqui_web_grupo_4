package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Inscripciones;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionesRepository extends JpaRepository<Inscripciones, Integer> {
    List<Inscripciones> findByEstudiantesId(int estudianteId);
    List<Inscripciones> findByCursosId(int cursoId);
    Optional<Inscripciones> findByEstudiantesIdAndCursosId(int estudianteId, int cursoId);
    boolean existsByEstudiantesIdAndCursosId(int estudianteId, int cursoId);
}
