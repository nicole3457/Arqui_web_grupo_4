package upc.edu.pe.banca_upcina.DTO;

import java.util.List;

public class CursoDetalleDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private String nivel;
    private String estado;
    private int categoriaId;
    private String categoriaNombre;
    private List<LeccionesDTO> lecciones;
    private int duracionTotalMinutos;

    public CursoDetalleDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public List<LeccionesDTO> getLecciones() {
        return lecciones;
    }

    public void setLecciones(List<LeccionesDTO> lecciones) {
        this.lecciones = lecciones;
    }

    public int getDuracionTotalMinutos() {
        return duracionTotalMinutos;
    }

    public void setDuracionTotalMinutos(int duracionTotalMinutos) {
        this.duracionTotalMinutos = duracionTotalMinutos;
    }
}
