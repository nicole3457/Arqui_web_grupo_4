package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
public class Inscripciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiantes estudiantes;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Cursos cursos;

    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    public Inscripciones() {
    }

    public Inscripciones(int id, Estudiantes estudiantes, Cursos cursos,
                         LocalDateTime fechaCompletado, LocalDate fechaInscripcion) {
        this.id = id;
        this.estudiantes = estudiantes;
        this.cursos = cursos;
        this.fechaCompletado = fechaCompletado;
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Estudiantes getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Estudiantes estudiantes) { this.estudiantes = estudiantes; }
    public Cursos getCursos() { return cursos; }
    public void setCursos(Cursos cursos) { this.cursos = cursos; }
    public LocalDateTime getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
}
