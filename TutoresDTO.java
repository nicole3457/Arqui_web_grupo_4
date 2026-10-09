package upc.edu.pe.banca_upcina.DTO;

import java.math.BigDecimal;
import java.util.Set;

public class TutoresDTO {
    private int id;
    private int userId;
    private String nombre;
    private String apellido;
    private String correo;
    private int experienciaAnios;
    private BigDecimal tarifa;
    private String estadoVerificacion;
    private Set<Integer> categoriaIds;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public int getExperienciaAnios() { return experienciaAnios; }
    public void setExperienciaAnios(int experienciaAnios) { this.experienciaAnios = experienciaAnios; }
    public BigDecimal getTarifa() { return tarifa; }
    public void setTarifa(BigDecimal tarifa) { this.tarifa = tarifa; }
    public String getEstadoVerificacion() { return estadoVerificacion; }
    public void setEstadoVerificacion(String estadoVerificacion) { this.estadoVerificacion = estadoVerificacion; }
    public Set<Integer> getCategoriaIds() { return categoriaIds; }
    public void setCategoriaIds(Set<Integer> categoriaIds) { this.categoriaIds = categoriaIds; }
}
