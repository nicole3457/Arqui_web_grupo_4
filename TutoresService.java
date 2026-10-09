package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Tutores;

import java.util.List;
import java.util.Optional;

public interface TutoresService {
    List<Tutores> list();
    Tutores insert(Tutores tutores);
    Optional<Tutores> getTutorId(int id);
    Optional<Tutores> findByUserId(int userId);
    boolean existsByUserId(int userId);
    List<Tutores> findVerificadosByCategoriaId(int categoriaId);
    Tutores update(Tutores tutores);
    void delete(int id);
}
