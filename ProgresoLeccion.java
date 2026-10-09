package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progreso_leccion")
public class ProgresoLeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "inscripcion_id")
    private Inscripciones inscripciones;

    @ManyToOne
    @JoinColumn(name = "leccion_id")
    private Lecciones lecciones;

    @Column(name = "completado")
    private boolean completado;

    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;

    public ProgresoLeccion() {
    }

    public ProgresoLeccion(int id, Inscripciones inscripciones, Lecciones lecciones,
                           boolean completado, LocalDateTime fechaCompletado) {
        this.id = id;
        this.inscripciones = inscripciones;
        this.lecciones = lecciones;
        this.completado = completado;
        this.fechaCompletado = fechaCompletado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Inscripciones getInscripciones() { return inscripciones; }
    public void setInscripciones(Inscripciones inscripciones) { this.inscripciones = inscripciones; }
    public Lecciones getLecciones() { return lecciones; }
    public void setLecciones(Lecciones lecciones) { this.lecciones = lecciones; }
    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }
    public LocalDateTime getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; }
}
