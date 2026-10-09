package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tutores")
public class Tutores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "correo")
    private String correo;

    @Column(name = "experiencia_anios")
    private int experienciaAnios;

    @Column(name = "tarifa")
    private BigDecimal tarifa;

    @Column(name = "estado_verificacion")
    private String estadoVerificacion;

    /*
     * tutor_categoria es una tabla puente.
     * Se maneja directamente con ManyToMany porque solo contiene
     * tutor_id y categoria_id.
     */
    @ManyToMany
    @JoinTable(
            name = "tutor_categoria",
            joinColumns = @JoinColumn(name = "tutor_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categorias> categorias = new HashSet<>();

    public Tutores() {
    }

    public Tutores(int id, Users users, String nombre, String apellido, String correo,
                   int experienciaAnios, BigDecimal tarifa, String estadoVerificacion) {
        this.id = id;
        this.users = users;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.experienciaAnios = experienciaAnios;
        this.tarifa = tarifa;
        this.estadoVerificacion = estadoVerificacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }
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
    public Set<Categorias> getCategorias() { return categorias; }
    public void setCategorias(Set<Categorias> categorias) { this.categorias = categorias; }
}
