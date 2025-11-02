package DAO;

/**
 * @author Angel
 */
import entities.Interaccion;
import entities.InteraccionPK;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IInteraccionDAO.
 */
public class InteraccionDAOImpl implements IInteraccionDAO {

    // Métodos CRUD Genéricos

    @Override
    public void crear(Interaccion entidad, EntityManager em) {
        // La clave (InteraccionPK) debe estar seteada ANTES de llamar a crear.
        em.persist(entidad);
    }

    @Override
    public Interaccion buscarPorId(InteraccionPK id, EntityManager em) {
        // Busca usando la clave primaria compuesta
        return em.find(Interaccion.class, id);
    }

    @Override
    public void actualizar(Interaccion entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(Interaccion entidad, EntityManager em) {
        if (!em.contains(entidad)) {
            Interaccion managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<Interaccion> listar(int limit, EntityManager em) {
        TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i", Interaccion.class);
        
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }

    // Métodos JPQL

    /**
     * Implementación de la búsqueda de una interacción existente.
     */
    @Override
    public Interaccion buscarInteraccion(Integer emisorId, Integer receptorId, EntityManager em) {
        try {
            // JPQL para buscar por los IDs de emisor y receptor
            // Nota como navegamos a través de las relaciones (i.emisor.id)
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.emisor.id = :emisorId AND i.receptor.id = :receptorId", 
                Interaccion.class);
            
            query.setParameter("emisorId", emisorId);
            query.setParameter("receptorId", receptorId);
            
            // Solo puede haber una interacción (o ninguna)
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            // Si no encuentra resultados, devuelve null
            return null; 
        }
    }
}
