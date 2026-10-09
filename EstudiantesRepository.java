package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;

import java.util.Optional;

@Repository
public interface EstudiantesRepository extends JpaRepository<Estudiantes, Integer> {
    Optional<Estudiantes> findByUsersId(int userId);
    boolean existsByUsersId(int userId);
}
