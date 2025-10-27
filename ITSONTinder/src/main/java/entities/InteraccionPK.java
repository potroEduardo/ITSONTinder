
package entities;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InteraccionPK implements Serializable {

    @Column(name = "emisorId")
    private Integer emisorId;

    @Column(name = "receptorId")
    private Integer receptorId;

    // Constructor vacío, getters, setters
    public InteraccionPK() {    
    }

    public InteraccionPK(Integer emisorId, Integer receptorId) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
    }

    public Integer getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(Integer emisorId) {
        this.emisorId = emisorId;
    }

    public Integer getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(Integer receptorId) {
        this.receptorId = receptorId;
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteraccionPK that = (InteraccionPK) o;
        return Objects.equals(emisorId, that.emisorId) &&
                Objects.equals(receptorId, that.receptorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emisorId, receptorId);
    }
}
