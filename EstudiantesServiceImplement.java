package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Estudiantes;
import upc.edu.pe.banca_upcina.Repositories.EstudiantesRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.EstudiantesService;

import java.util.List;
import java.util.Optional;

@Service
public class EstudiantesServiceImplement implements EstudiantesService {

    @Autowired
    private EstudiantesRepository eR;

    @Override
    public List<Estudiantes> list() {
        return eR.findAll();
    }

    @Override
    public Estudiantes insert(Estudiantes estudiantes) {
        return eR.save(estudiantes);
    }

    @Override
    public Optional<Estudiantes> getEstudianteId(int id) {
        return eR.findById(id);
    }

    @Override
    public Optional<Estudiantes> findByUserId(int userId) {
        return eR.findByUsersId(userId);
    }

    @Override
    public boolean existsByUserId(int userId) {
        return eR.existsByUsersId(userId);
    }

    @Override
    public Estudiantes update(Estudiantes estudiantes) {
        return eR.save(estudiantes);
    }

    @Override
    public void delete(int id) {
        eR.deleteById(id);
    }
}
