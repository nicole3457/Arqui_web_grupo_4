package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Authorities;

import java.util.List;
import java.util.Optional;

public interface AuthoritiesService {
    List<Authorities> list();
    Authorities insert(Authorities authorities);
    Optional<Authorities> getAuthorityId(int id);
    Optional<Authorities> findByName(String name);
    void delete(int id);
}
