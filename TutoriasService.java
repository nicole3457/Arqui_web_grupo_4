package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Tutorias;

import java.util.List;
import java.util.Optional;

public interface TutoriasService {
    List<Tutorias> list();
    Tutorias insert(Tutorias tutorias);
    Optional<Tutorias> getTutoriaId(int id);
    List<Tutorias> findByEstudianteId(int estudianteId);
    List<Tutorias> findByTutorId(int tutorId);
    List<Tutorias> findByTutorIdAndEstado(int tutorId, String estado);
    boolean existsConflict(int tutorId, java.time.LocalDateTime fechaHora);
    Tutorias update(Tutorias tutorias);
    void delete(int id);
}
