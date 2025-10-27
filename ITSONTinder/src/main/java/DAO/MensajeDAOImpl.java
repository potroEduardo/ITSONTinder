/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Mensaje;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IMensajeDAO.
 * Contiene la lógica de persistencia y las consultas JPQL.
 */
public class MensajeDAOImpl implements IMensajeDAO {

    // --- Métodos CRUD Genéricos ---

    @Override
    public void crear(Mensaje entidad, EntityManager em) {
        em.persist(entidad);
    }

    @Override
    public Mensaje buscarPorId(Integer id, EntityManager em) {
        return em.find(Mensaje.class, id);
    }

    @Override
    public void actualizar(Mensaje entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(Mensaje entidad, EntityManager em) {
        if (!em.contains(entidad)) {
            Mensaje managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<Mensaje> listar(int limit, EntityManager em) {
        TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m", Mensaje.class);
        
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }

    // --- Métodos JPQL Específicos ---

    /**
     * Implementación de la búsqueda de mensajes por match, ordenados por fecha.
     */
    @Override
    public List<Mensaje> listarMensajesPorMatch(Integer matchId, EntityManager em) {
        // JPQL para buscar mensajes por ID de match y ordenarlos por fecha ascendente
        TypedQuery<Mensaje> query = em.createQuery(
            "SELECT m FROM Mensaje m WHERE m.match.id = :matchId ORDER BY m.fecha ASC", 
            Mensaje.class);
        
        query.setParameter("matchId", matchId);
        
        return query.getResultList();
    }
}
