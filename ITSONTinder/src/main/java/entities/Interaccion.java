package entities;

import entities.InteraccionPK;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import converters.TipoInteraccionConverter;

@Entity
@Table(name = "INTERACCION")
public class Interaccion {

    @EmbeddedId
    private InteraccionPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("emisorId") // Mapea la parte 'emisorId' del @EmbeddedId
    @JoinColumn(name = "emisorId")
    private Estudiante emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("receptorId") // Mapea la parte 'receptorId' del @EmbeddedId
    @JoinColumn(name = "receptorId")
    private Estudiante receptor;
    
    @Convert(converter = TipoInteraccionConverter.class)
    @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('Me gusta', 'No me interesa')")
    // No se necesita @Enumerated gracias al AttributeConverter
    private TipoInteraccion tipo;

    @Column(name = "fecha", 
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", 
            insertable = false, 
            updatable = false)
    private LocalDateTime fecha;

    // Getters y Setters

    public Interaccion() {
    }

    public Interaccion(InteraccionPK id, Estudiante emisor, Estudiante receptor, TipoInteraccion tipo, LocalDateTime fecha) {
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public InteraccionPK getId() {
        return id;
    }

    public void setId(InteraccionPK id) {
        this.id = id;
    }

    public Estudiante getEmisor() {
        return emisor;
    }

    public void setEmisor(Estudiante emisor) {
        this.emisor = emisor;
    }

    public Estudiante getReceptor() {
        return receptor;
    }

    public void setReceptor(Estudiante receptor) {
        this.receptor = receptor;
    }

    public TipoInteraccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoInteraccion tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}