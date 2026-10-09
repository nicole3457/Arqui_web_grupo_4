package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Tutores;
import upc.edu.pe.banca_upcina.Repositories.TutoresRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoresService;

import java.util.List;
import java.util.Optional;

@Service
public class TutoresServiceImplement implements TutoresService {

    @Autowired
    private TutoresRepository tR;

    @Override
    public List<Tutores> list() {
        return tR.findAll();
    }

    @Override
    public Tutores insert(Tutores tutores) {
        return tR.save(tutores);
    }

    @Override
    public Optional<Tutores> getTutorId(int id) {
        return tR.findById(id);
    }

    @Override
    public Optional<Tutores> findByUserId(int userId) {
        return tR.findByUsersId(userId);
    }

    @Override
    public boolean existsByUserId(int userId) {
        return tR.existsByUsersId(userId);
    }

    @Override
    public List<Tutores> findVerificadosByCategoriaId(int categoriaId) {
        return tR.findVerificadosByCategoriaId(categoriaId);
    }

    @Override
    public Tutores update(Tutores tutores) {
        return tR.save(tutores);
    }

    @Override
    public void delete(int id) {
        tR.deleteById(id);
    }
}
