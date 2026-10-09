package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDateTime;

public class TutoriasDTO {
    private int id;
    private int estudianteId;
    private int tutorId;
    private int categoriaId;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private String estado;
    private String enlaceVideo;
    private Integer calificacion;
    private String comentario;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }
    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }
    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
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
