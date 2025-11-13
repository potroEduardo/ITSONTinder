package DAO;

import entities.Mensaje;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IMensajeDAO.
 * @author Angel Beltran
 */
public class MensajeDAOImpl implements IMensajeDAO {

    // Métodos CRUD Genéricos

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

    //Métodos JPQL Específicos

    // Busqueda de mensajes por match
    @Override
    public List<Mensaje> listarMensajesPorMatch(Integer matchId, EntityManager em) {
        
        TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m JOIN FETCH m.emisor WHERE m.match.id = :matchId ORDER BY m.fecha ASC", 
                Mensaje.class);

        query.setParameter("matchId", matchId);
        
        return query.getResultList();
    }
}
