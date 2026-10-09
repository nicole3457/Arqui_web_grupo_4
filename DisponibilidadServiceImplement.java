package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Disponibilidad;
import upc.edu.pe.banca_upcina.Repositories.DisponibilidadRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.DisponibilidadService;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadServiceImplement implements DisponibilidadService {

    @Autowired
    private DisponibilidadRepository dR;

    @Override
    public List<Disponibilidad> list() {
        return dR.findAll();
    }

    @Override
    public Disponibilidad insert(Disponibilidad disponibilidad) {
        return dR.save(disponibilidad);
    }

    @Override
    public Optional<Disponibilidad> getDisponibilidadId(int id) {
        return dR.findById(id);
    }

    @Override
    public List<Disponibilidad> findByTutorId(int tutorId) {
        return dR.findByTutoresId(tutorId);
    }

    @Override
    public Disponibilidad update(Disponibilidad disponibilidad) {
        return dR.save(disponibilidad);
    }

    @Override
    public void delete(int id) {
        dR.deleteById(id);
    }
}
