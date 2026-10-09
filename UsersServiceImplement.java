package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Users;
import upc.edu.pe.banca_upcina.Repositories.UsersRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.UsersService;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImplement implements UsersService {

    @Autowired
    private UsersRepository uR;

    @Override
    public List<Users> list() {
        return uR.findAll();
    }

    @Override
    public Users insert(Users users) {
        return uR.save(users);
    }

    @Override
    public Optional<Users> getUserId(int id) {
        return uR.findById(id);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return uR.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return uR.existsByUsername(username);
    }

    @Override
    public Users update(Users users) {
        return uR.save(users);
    }

    @Override
    public void delete(int id) {
        uR.deleteById(id);
    }
}
