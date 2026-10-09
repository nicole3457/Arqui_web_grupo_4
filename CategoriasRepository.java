package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Categorias;

import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {
    Optional<Categorias> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
