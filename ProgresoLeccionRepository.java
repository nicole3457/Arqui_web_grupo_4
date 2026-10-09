package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.ProgresoLeccion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoLeccionRepository extends JpaRepository<ProgresoLeccion, Integer> {
    List<ProgresoLeccion> findByInscripcionesId(int inscripcionId);
    Optional<ProgresoLeccion> findByInscripcionesIdAndLeccionesId(int inscripcionId, int leccionId);
    long countByInscripcionesIdAndCompletadoTrue(int inscripcionId);
}
