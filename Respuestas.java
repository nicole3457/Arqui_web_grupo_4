package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
public class Respuestas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    private Preguntas preguntas;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Users users;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Respuestas() {
    }

    public Respuestas(int id, Preguntas preguntas, Users users, String contenido, LocalDateTime fechaCreacion) {
        this.id = id;
        this.preguntas = preguntas;
        this.users = users;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Preguntas getPreguntas() { return preguntas; }
    public void setPreguntas(Preguntas preguntas) { this.preguntas = preguntas; }
    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
