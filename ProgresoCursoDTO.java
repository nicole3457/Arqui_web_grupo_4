package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalDateTime;

public class ProgresoCursoDTO {
    private int inscripcionId;
    private int estudianteId;
    private int cursoId;
    private String cursoNombre;
    private int totalLecciones;
    private int leccionesCompletadas;
    private double porcentajeProgreso;
    private String estadoCurso;
    private LocalDateTime fechaCompletado;

    public ProgresoCursoDTO() {
    }

    public ProgresoCursoDTO(int inscripcionId, int estudianteId, int cursoId, String cursoNombre,
                             int totalLecciones, int leccionesCompletadas, double porcentajeProgreso,
                             String estadoCurso, LocalDateTime fechaCompletado) {
        this.inscripcionId = inscripcionId;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.cursoNombre = cursoNombre;
        this.totalLecciones = totalLecciones;
        this.leccionesCompletadas = leccionesCompletadas;
        this.porcentajeProgreso = porcentajeProgreso;
        this.estadoCurso = estadoCurso;
        this.fechaCompletado = fechaCompletado;
    }

    public int getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(int inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
    }

    public int getTotalLecciones() {
        return totalLecciones;
    }

    public void setTotalLecciones(int totalLecciones) {
        this.totalLecciones = totalLecciones;
    }

    public int getLeccionesCompletadas() {
        return leccionesCompletadas;
    }

    public void setLeccionesCompletadas(int leccionesCompletadas) {
        this.leccionesCompletadas = leccionesCompletadas;
    }

    public double getPorcentajeProgreso() {
        return porcentajeProgreso;
    }

    public void setPorcentajeProgreso(double porcentajeProgreso) {
        this.porcentajeProgreso = porcentajeProgreso;
    }

    public String getEstadoCurso() {
        return estadoCurso;
    }

    public void setEstadoCurso(String estadoCurso) {
        this.estadoCurso = estadoCurso;
    }

    public LocalDateTime getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(LocalDateTime fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }
}
