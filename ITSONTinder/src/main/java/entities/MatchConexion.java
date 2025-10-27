package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MATCH_CONEXION",
       uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante1Id", "estudiante2Id"})
)
public class MatchConexion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante1Id")
    private Estudiante estudiante1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante2Id")
    private Estudiante estudiante2;

    @Column(name = "fecha",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes;

    // Getters y Setters

    public MatchConexion() {
    }

    public MatchConexion(Integer id, Estudiante estudiante1, Estudiante estudiante2, LocalDateTime fecha, List<Mensaje> mensajes) {
        this.id = id;
        this.estudiante1 = estudiante1;
        this.estudiante2 = estudiante2;
        this.fecha = fecha;
        this.mensajes = mensajes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudiante getEstudiante1() {
        return estudiante1;
    }

    public void setEstudiante1(Estudiante estudiante1) {
        this.estudiante1 = estudiante1;
    }

    public Estudiante getEstudiante2() {
        return estudiante2;
    }

    public void setEstudiante2(Estudiante estudiante2) {
        this.estudiante2 = estudiante2;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
}
