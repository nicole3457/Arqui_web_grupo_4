package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lecciones")
public class Lecciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Cursos cursos;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "url_video")
    private String urlVideo;

    @Column(name = "duracion_minutos")
    private int duracionMinutos;

    @Column(name = "orden")
    private int orden;

    public Lecciones() {
    }

    public Lecciones(int id, Cursos cursos, String titulo, String descripcion, String urlVideo,
                     int duracionMinutos, int orden) {
        this.id = id;
        this.cursos = cursos;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlVideo = urlVideo;
        this.duracionMinutos = duracionMinutos;
        this.orden = orden;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cursos getCursos() { return cursos; }
    public void setCursos(Cursos cursos) { this.cursos = cursos; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUrlVideo() { return urlVideo; }
    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }
    public String getEnlaceVideo() { return urlVideo; }
    public void setEnlaceVideo(String enlaceVideo) { this.urlVideo = enlaceVideo; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
}
