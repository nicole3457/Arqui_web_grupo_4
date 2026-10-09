package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDateTime;

public class RespuestasDTO {
    private int id;
    private String contenido;
    private LocalDateTime fecha_creacion;
    private int preguntas;
    private int users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fecha_creacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fecha_creacion = fechaCreacion;
    }

    public int getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(int preguntas) {
        this.preguntas = preguntas;
    }

    public int getPreguntaId() {
        return preguntas;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntas = preguntaId;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getUsuarioId() {
        return users;
    }

    public void setUsuarioId(int usuarioId) {
        this.users = usuarioId;
    }

    private boolean esExperto;

    public boolean isEsExperto() {
        return esExperto;
    }

    public void setEsExperto(boolean esExperto) {
        this.esExperto = esExperto;
    }
}
