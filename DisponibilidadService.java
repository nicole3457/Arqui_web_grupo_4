package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Disponibilidad;

import java.util.List;
import java.util.Optional;

public interface DisponibilidadService {
    List<Disponibilidad> list();
    Disponibilidad insert(Disponibilidad disponibilidad);
    Optional<Disponibilidad> getDisponibilidadId(int id);
    List<Disponibilidad> findByTutorId(int tutorId);
    Disponibilidad update(Disponibilidad disponibilidad);
    void delete(int id);
}
