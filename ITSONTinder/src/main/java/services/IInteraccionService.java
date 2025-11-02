package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * para la entidad Interaccion (Lógica de Negocio).
 *
 * @author Angel
 */
import entities.Interaccion;
import entities.TipoInteraccion;
import java.util.List; 

public interface IInteraccionService {

    /**
     * Registra una acción (ME_GUSTA o NO_ME_INTERESA) del emisor hacia el receptor.
     */
    boolean registrarAccion(Integer emisorId, Integer receptorId, TipoInteraccion tipo);

    /**
     * Busca una interacción existente entre dos estudiantes en una dirección específica (Emisor -> Receptor).
     */
    Interaccion buscarInteraccionExistente(Integer emisorId, Integer receptorId);

    /**
     * Actualiza el tipo de una acción existente (por ejemplo, cambiar de NO_ME_INTERESA a ME_GUSTA).
     */
    void actualizarAccion(Integer emisorId, Integer receptorId, TipoInteraccion nuevoTipo);

    /**
     * Elimina una interacción específica del sistema.
     */
    void eliminarAccion(Integer emisorId, Integer receptorId);
    
}