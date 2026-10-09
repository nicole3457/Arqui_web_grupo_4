package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.ProgresoLeccion;
import upc.edu.pe.banca_upcina.Repositories.ProgresoLeccionRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.ProgresoLeccionService;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresoLeccionServiceImplement implements ProgresoLeccionService {

    @Autowired
    private ProgresoLeccionRepository pLR;

    @Override
    public List<ProgresoLeccion> list() {
        return pLR.findAll();
    }

    @Override
    public ProgresoLeccion insert(ProgresoLeccion progresoLeccion) {
        return pLR.save(progresoLeccion);
    }

    @Override
    public Optional<ProgresoLeccion> getProgresoLeccionId(int id) {
        return pLR.findById(id);
    }

    @Override
    public List<ProgresoLeccion> findByInscripcionId(int inscripcionId) {
        return pLR.findByInscripcionesId(inscripcionId);
    }

    @Override
    public Optional<ProgresoLeccion> findByInscripcionIdAndLeccionId(int inscripcionId, int leccionId) {
        return pLR.findByInscripcionesIdAndLeccionesId(inscripcionId, leccionId);
    }

    @Override
    public long countCompletadas(int inscripcionId) {
        return pLR.countByInscripcionesIdAndCompletadoTrue(inscripcionId);
    }

    @Override
    public ProgresoLeccion update(ProgresoLeccion progresoLeccion) {
        return pLR.save(progresoLeccion);
    }

    @Override
    public void delete(int id) {
        pLR.deleteById(id);
    }
}
