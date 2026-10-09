package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Authorities;
import upc.edu.pe.banca_upcina.Repositories.AuthoritiesRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.AuthoritiesService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthoritiesServiceImplement implements AuthoritiesService {

    @Autowired
    private AuthoritiesRepository aR;

    @Override
    public List<Authorities> list() {
        return aR.findAll();
    }

    @Override
    public Authorities insert(Authorities authorities) {
        return aR.save(authorities);
    }

    @Override
    public Optional<Authorities> getAuthorityId(int id) {
        return aR.findById(id);
    }

    @Override
    public Optional<Authorities> findByName(String name) {
        return aR.findByNameIgnoreCase(name);
    }

    @Override
    public void delete(int id) {
        aR.deleteById(id);
    }
}
