package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "preguntas")
public class Preguntas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categorias categorias;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Preguntas() {
    }

    public Preguntas(int id, Users users, Categorias categorias, String titulo,
                     String contenido, LocalDateTime fechaCreacion) {
        this.id = id;
        this.users = users;
        this.categorias = categorias;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }
    public Categorias getCategorias() { return categorias; }
    public void setCategorias(Categorias categorias) { this.categorias = categorias; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
