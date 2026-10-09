package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiantes {
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

    @Column(name = "universidad")
    private String universidad;

    @Column(name = "carrera")
    private String carrera;

    @Column(name = "ciclo")
    private int ciclo;

    public Estudiantes() {
    }

    public Estudiantes(int id, Users users, String nombre, String apellido, String correo,
                       String universidad, String carrera, int ciclo) {
        this.id = id;
        this.users = users;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.universidad = universidad;
        this.carrera = carrera;
        this.ciclo = ciclo;
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
    public String getUniversidad() { return universidad; }
    public void setUniversidad(String universidad) { this.universidad = universidad; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public int getCiclo() { return ciclo; }
    public void setCiclo(int ciclo) { this.ciclo = ciclo; }
}
