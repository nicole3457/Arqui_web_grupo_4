package upc.edu.pe.banca_upcina.ServiceInterfaces;

import upc.edu.pe.banca_upcina.Entities.Categorias;

import java.util.List;
import java.util.Optional;

public interface CategoriasService {
    List<Categorias> list();
    Categorias insert(Categorias categorias);
    Optional<Categorias> getCategoriaId(int id);
    Optional<Categorias> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    Categorias update(Categorias categorias);
    void delete(int id);
}
