package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidad")
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutores tutores;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    public Disponibilidad() {
    }

    public Disponibilidad(int id, Tutores tutores, String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.tutores = tutores;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Tutores getTutores() { return tutores; }
    public void setTutores(Tutores tutores) { this.tutores = tutores; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
