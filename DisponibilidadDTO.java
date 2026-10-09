package upc.edu.pe.banca_upcina.DTO;

import java.time.LocalTime;

public class DisponibilidadDTO {
    private int id;
    private int tutorId;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
