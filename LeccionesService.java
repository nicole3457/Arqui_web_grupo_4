package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Lecciones;

import java.util.List;
import java.util.Optional;

public interface LeccionesService {
    List<Lecciones> list();
    Lecciones insert(Lecciones lecciones);
    Optional<Lecciones> getLeccionId(int id);
    List<Lecciones> findByCursoId(int cursoId);
    long countByCursoId(int cursoId);
    Lecciones update(Lecciones lecciones);
    void delete(int id);
}
