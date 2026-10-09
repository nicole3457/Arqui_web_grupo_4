package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Tutorias;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TutoriasRepository extends JpaRepository<Tutorias, Integer> {
    List<Tutorias> findByEstudiantesId(int estudianteId);
    List<Tutorias> findByTutoresId(int tutorId);
    List<Tutorias> findByTutoresIdAndEstadoIgnoreCase(int tutorId, String estado);
    boolean existsByTutoresIdAndFechaHoraAndEstadoIgnoreCase(int tutorId, LocalDateTime fechaHora, String estado);
}
