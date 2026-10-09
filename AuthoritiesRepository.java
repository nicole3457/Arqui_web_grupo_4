package upc.edu.pe.banca_upcina.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.banca_upcina.Entities.Authorities;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    Optional<Authorities> findByNameIgnoreCase(String name);
}
