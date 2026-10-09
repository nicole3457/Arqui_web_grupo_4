package upc.edu.pe.banca_upcina.DTO;

public class CursosDTO {
    private int id;
    private int categoriaId;
    private String nombre;
    private String descripcion;
    private String nivel;
    private String estado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
