package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Tutorias;
import upc.edu.pe.banca_upcina.Repositories.TutoriasRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.TutoriasService;

import java.util.List;
import java.util.Optional;

@Service
public class TutoriasServiceImplement implements TutoriasService {

    @Autowired
    private TutoriasRepository tR;

    @Override
    public List<Tutorias> list() {
        return tR.findAll();
    }

    @Override
    public Tutorias insert(Tutorias tutorias) {
        return tR.save(tutorias);
    }

    @Override
    public Optional<Tutorias> getTutoriaId(int id) {
        return tR.findById(id);
    }

    @Override
    public List<Tutorias> findByEstudianteId(int estudianteId) {
        return tR.findByEstudiantesId(estudianteId);
    }

    @Override
    public List<Tutorias> findByTutorId(int tutorId) {
        return tR.findByTutoresId(tutorId);
    }

    @Override
    public List<Tutorias> findByTutorIdAndEstado(int tutorId, String estado) {
        return tR.findByTutoresIdAndEstadoIgnoreCase(tutorId, estado);
    }

    @Override
    public boolean existsConflict(int tutorId, java.time.LocalDateTime fechaHora) {
        return tR.existsByTutoresIdAndFechaHoraAndEstadoIgnoreCase(tutorId, fechaHora, "programada");
    }

    @Override
    public Tutorias update(Tutorias tutorias) {
        return tR.save(tutorias);
    }

    @Override
    public void delete(int id) {
        tR.deleteById(id);
    }
}
