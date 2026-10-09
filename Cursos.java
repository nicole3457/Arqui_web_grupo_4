package upc.edu.pe.banca_upcina.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categorias categorias;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "estado")
    private String estado;

    public Cursos() {
    }

    public Cursos(int id, Categorias categorias, String nombre, String descripcion, String nivel, String estado) {
        this.id = id;
        this.categorias = categorias;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Categorias getCategorias() { return categorias; }
    public void setCategorias(Categorias categorias) { this.categorias = categorias; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
