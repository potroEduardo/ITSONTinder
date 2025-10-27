package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MENSAJE")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchId", nullable = false)
    private MatchConexion match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisorId", nullable = false)
    private Estudiante emisor;

    @Lob 
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fecha",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false,
            updatable = false)
    private LocalDateTime fecha;

    // Getters y Setters

    public Mensaje() {
    }

    public Mensaje(Integer id, MatchConexion match, Estudiante emisor, String contenido, LocalDateTime fecha) {
        this.id = id;
        this.match = match;
        this.emisor = emisor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MatchConexion getMatch() {
        return match;
    }

    public void setMatch(MatchConexion match) {
        this.match = match;
    }

    public Estudiante getEmisor() {
        return emisor;
    }

    public void setEmisor(Estudiante emisor) {
        this.emisor = emisor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}
