/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Mensaje;
import java.util.List;

/**
 * Interfaz para la capa de servicio de la entidad Mensaje.
 * Define la lógica de negocio para el módulo de chat.
 */
public interface IMensajeService {

    /**
     * Lógica de negocio principal: Enviar un nuevo mensaje.
     *
     * @param matchId El ID del match al que pertenece el chat.
     * @param emisorId El ID del estudiante que envía el mensaje.
     * @param contenido El texto del mensaje.
     * @return El objeto Mensaje recién creado (con su ID y timestamp).
     */
    Mensaje enviarMensaje(Integer matchId, Integer emisorId, String contenido);

    /**
     * Lógica de negocio principal: Carga todos los mensajes de un chat (match).
     *
     * @param matchId El ID del match del cual se quiere cargar el historial.
     * @return Una lista de Mensajes, ordenada por fecha.
     */
    List<Mensaje> cargarHistorialDeChat(Integer matchId);

    /**
     * Elimina un mensaje por su ID.
     *
     * @param mensajeId El ID del mensaje a eliminar.
     */
    void eliminarMensaje(Integer mensajeId);
    
    /**
     * Busca un mensaje por su ID.
     * @param mensajeId
     * @return El Mensaje, o null.
     */
    Mensaje buscarMensajePorId(Integer mensajeId);
}