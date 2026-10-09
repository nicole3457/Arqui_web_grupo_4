package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDateTime;

public class PreguntasDTO {
    private int id;
    private String titulo;
    private String contenido;
    private LocalDateTime fecha_creacion;
    private int usuario_id;
    private int categorias_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public int getCategorias_id() {
        return categorias_id;
    }

    public void setCategorias_id(int categorias_id) {
        this.categorias_id = categorias_id;
    }

    public int getCategoriaId() {
        return categorias_id;
    }

    public void setCategoriaId(int categoriaId) {
        this.categorias_id = categoriaId;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuario_id = usuarioId;
    }
}
