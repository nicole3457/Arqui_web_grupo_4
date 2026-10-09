package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> list();
    Users insert(Users users);
    Optional<Users> getUserId(int id);
    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);
    Users update(Users users);
    void delete(int id);
}
