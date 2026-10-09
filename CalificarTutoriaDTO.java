package upc.edu.pe.banca_upcina.DTO;

public class CalificarTutoriaDTO {
    private int calificacion;
    private String comentario;

    public CalificarTutoriaDTO() {
    }

    public CalificarTutoriaDTO(int calificacion, String comentario) {
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
