package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.ProgresoLeccion;

import java.util.List;
import java.util.Optional;

public interface ProgresoLeccionService {
    List<ProgresoLeccion> list();
    ProgresoLeccion insert(ProgresoLeccion progresoLeccion);
    Optional<ProgresoLeccion> getProgresoLeccionId(int id);
    List<ProgresoLeccion> findByInscripcionId(int inscripcionId);
    Optional<ProgresoLeccion> findByInscripcionIdAndLeccionId(int inscripcionId, int leccionId);
    long countCompletadas(int inscripcionId);
    ProgresoLeccion update(ProgresoLeccion progresoLeccion);
    void delete(int id);
}
