package upc.edu.pe.banca_upcina.DTO;

public class LeccionesDTO {
    private int id;
    private int cursoId;
    private String titulo;
    private String descripcion;
    private String urlVideo;
    private int duracionMinutos;
    private int orden;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
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
