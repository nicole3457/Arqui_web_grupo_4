package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Estudiantes;

import java.util.List;
import java.util.Optional;

public interface EstudiantesService {
    List<Estudiantes> list();
    Estudiantes insert(Estudiantes estudiantes);
    Optional<Estudiantes> getEstudianteId(int id);
    Optional<Estudiantes> findByUserId(int userId);
    boolean existsByUserId(int userId);
    Estudiantes update(Estudiantes estudiantes);
    void delete(int id);
}
