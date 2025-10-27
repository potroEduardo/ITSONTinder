/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Mensaje;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO específica para la entidad Mensaje.
 * Hereda los métodos CRUD de IGenericDAO.
 */
public interface IMensajeDAO extends IGenericDAO<Mensaje, Integer> {

    /**
     * Busca todos los mensajes de un match específico, ordenados por fecha ascendente.
     * Esencial para cargar el historial de un chat.
     *
     * @param matchId El ID del MatchConexion.
     * @param em El EntityManager.
     * @return Una lista de Mensajes ordenados.
     */
    List<Mensaje> listarMensajesPorMatch(Integer matchId, EntityManager em);
    
}
