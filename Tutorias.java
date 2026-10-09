package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutorias")
public class Tutorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiantes estudiantes;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutores tutores;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categorias categorias;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "duracion_minutos")
    private int duracionMinutos;

    @Column(name = "estado")
    private String estado;

    @Column(name = "enlace_video")
    private String enlaceVideo;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "comentario")
    private String comentario;

    public Tutorias() {
    }

    public Tutorias(int id, Estudiantes estudiantes, Tutores tutores, Categorias categorias,
                    LocalDateTime fechaHora, int duracionMinutos, String estado,
                    String enlaceVideo, Integer calificacion, String comentario) {
        this.id = id;
        this.estudiantes = estudiantes;
        this.tutores = tutores;
        this.categorias = categorias;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;
        this.enlaceVideo = enlaceVideo;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Estudiantes getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Estudiantes estudiantes) { this.estudiantes = estudiantes; }
    public Tutores getTutores() { return tutores; }
    public void setTutores(Tutores tutores) { this.tutores = tutores; }
    public Categorias getCategorias() { return categorias; }
    public void setCategorias(Categorias categorias) { this.categorias = categorias; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEnlaceVideo() { return enlaceVideo; }
    public void setEnlaceVideo(String enlaceVideo) { this.enlaceVideo = enlaceVideo; }
    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
