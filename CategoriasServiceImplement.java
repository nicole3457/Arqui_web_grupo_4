package upc.edu.pe.banca_upcina.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.banca_upcina.Entities.Categorias;
import upc.edu.pe.banca_upcina.Repositories.CategoriasRepository;
import upc.edu.pe.banca_upcina.ServiceInterfaces.CategoriasService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasServiceImplement implements CategoriasService {

    @Autowired
    private CategoriasRepository cR;

    @Override
    public List<Categorias> list() {
        return cR.findAll();
    }

    @Override
    public Categorias insert(Categorias categorias) {
        return cR.save(categorias);
    }

    @Override
    public Optional<Categorias> getCategoriaId(int id) {
        return cR.findById(id);
    }

    @Override
    public Optional<Categorias> findByNombre(String nombre) {
        return cR.findByNombreIgnoreCase(nombre);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return cR.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public Categorias update(Categorias categorias) {
        return cR.save(categorias);
    }

    @Override
    public void delete(int id) {
        cR.deleteById(id);
    }
}
