package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * @author Angel
 */
import entities.Mensaje;
import java.util.List;

public interface IMensajeService {

    /**
     * Envía un nuevo mensaje dentro de un chat de MatchConexion.
     */
    Mensaje enviarMensaje(Integer matchId, Integer emisorId, String contenido);

    /**
     * Carga el historial completo de mensajes para un MatchConexion específico.
     */
    List<Mensaje> cargarHistorialDeChat(Integer matchId);

    /**
     * Elimina un mensaje específico del sistema por su ID.
     */
    void eliminarMensaje(Integer mensajeId);
    
    /**
     * Busca un mensaje por su identificador único.
     */
    Mensaje buscarMensajePorId(Integer mensajeId);
}