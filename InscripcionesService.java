package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Inscripciones;

import java.util.List;
import java.util.Optional;

public interface InscripcionesService {
    List<Inscripciones> list();
    Inscripciones insert(Inscripciones inscripciones);
    Optional<Inscripciones> getInscripcionId(int id);
    List<Inscripciones> findByEstudianteId(int estudianteId);
    List<Inscripciones> findByCursoId(int cursoId);
    Optional<Inscripciones> findByEstudianteIdAndCursoId(int estudianteId, int cursoId);
    boolean existsByEstudianteIdAndCursoId(int estudianteId, int cursoId);
    Inscripciones update(Inscripciones inscripciones);
    void delete(int id);
}
