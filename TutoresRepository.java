package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Tutores;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutoresRepository extends JpaRepository<Tutores, Integer> {
    Optional<Tutores> findByUsersId(int userId);
    boolean existsByUsersId(int userId);

    @Query("SELECT DISTINCT t FROM Tutores t JOIN t.categorias c WHERE c.id = :categoriaId AND LOWER(t.estadoVerificacion) = 'verificado'")
    List<Tutores> findVerificadosByCategoriaId(@Param("categoriaId") int categoriaId);
}
