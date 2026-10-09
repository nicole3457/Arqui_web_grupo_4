package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Inscripciones;
import upc.edu.pe.banca_upcina.Repositories.InscripcionesRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.InscripcionesService;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionesServiceImplement implements InscripcionesService {

    @Autowired
    private InscripcionesRepository iR;

    @Override
    public List<Inscripciones> list() {
        return iR.findAll();
    }

    @Override
    public Inscripciones insert(Inscripciones inscripciones) {
        return iR.save(inscripciones);
    }

    @Override
    public Optional<Inscripciones> getInscripcionId(int id) {
        return iR.findById(id);
    }

    @Override
    public List<Inscripciones> findByEstudianteId(int estudianteId) {
        return iR.findByEstudiantesId(estudianteId);
    }

    @Override
    public List<Inscripciones> findByCursoId(int cursoId) {
        return iR.findByCursosId(cursoId);
    }

    @Override
    public Optional<Inscripciones> findByEstudianteIdAndCursoId(int estudianteId, int cursoId) {
        return iR.findByEstudiantesIdAndCursosId(estudianteId, cursoId);
    }

    @Override
    public boolean existsByEstudianteIdAndCursoId(int estudianteId, int cursoId) {
        return iR.existsByEstudiantesIdAndCursosId(estudianteId, cursoId);
    }

    @Override
    public Inscripciones update(Inscripciones inscripciones) {
        return iR.save(inscripciones);
    }

    @Override
    public void delete(int id) {
        iR.deleteById(id);
    }
}
