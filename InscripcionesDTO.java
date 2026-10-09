package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InscripcionesDTO {
    private int id;
    private int estudianteId;
    private int cursoId;
    private LocalDateTime fechaCompletado;
    private LocalDate fechaInscripcion;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }
    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    public LocalDateTime getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}
