package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Cursos;

import java.util.List;
import java.util.Optional;

public interface CursosService {
    List<Cursos> list();
    Cursos insert(Cursos cursos);
    Optional<Cursos> getCursoId(int id);
    List<Cursos> findByCategoriaId(int categoriaId);
    List<Cursos> findByEstado(String estado);
    List<Cursos> findCatalogo(Integer categoriaId, String nivel);
    Cursos update(Cursos cursos);
    void delete(int id);
}
