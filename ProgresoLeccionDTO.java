package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDateTime;

public class ProgresoLeccionDTO {
    private int id;
    private int inscripcionId;
    private int leccionId;
    private boolean completado;
    private LocalDateTime fechaCompletado;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getInscripcionId() { return inscripcionId; }
    public void setInscripcionId(int inscripcionId) { this.inscripcionId = inscripcionId; }
    public int getLeccionId() { return leccionId; }
    public void setLeccionId(int leccionId) { this.leccionId = leccionId; }
    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }
    public LocalDateTime getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; }
}
