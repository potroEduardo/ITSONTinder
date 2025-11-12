package DAO;

/**
 * @author Angel
 */
import entities.MatchConexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IMatchConexionDAO.
 */
public class MatchConexionDAOImpl implements IMatchConexionDAO {

    // Métodos CRUD Genéricos 

    @Override
    public void crear(MatchConexion entidad, EntityManager em) {
        em.persist(entidad);
    }

    @Override
    public MatchConexion buscarPorId(Integer id, EntityManager em) {
        return em.find(MatchConexion.class, id);
    }

    @Override
    public void actualizar(MatchConexion entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(MatchConexion entidad, EntityManager em) {
        if (!em.contains(entidad)) {
            MatchConexion managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<MatchConexion> listar(int limit, EntityManager em) {
        TypedQuery<MatchConexion> query = em.createQuery(
                "SELECT m FROM MatchConexion m", MatchConexion.class);
        
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }

    // Métodos JPQL Específicos

    /**
     * Busca y devuelve todos los matches (conexiones) de un estudiante.
     */
    @Override
    public List<MatchConexion> listarMatchesPorEstudiante(Integer estudianteId, EntityManager em) {

        TypedQuery<MatchConexion> query = em.createQuery(
                "SELECT m FROM MatchConexion m JOIN FETCH m.estudiante1 JOIN FETCH m.estudiante2 WHERE m.estudiante1.id = :estudianteId OR m.estudiante2.id = :estudianteId", 
                MatchConexion.class);
        // ---- FIN DE LA CORRECCIÓN ----
        
        query.setParameter("estudianteId", estudianteId);
        
        return query.getResultList();
    }

    /**
     * Busca un match existente 
     */
    @Override
    public MatchConexion buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id, EntityManager em) {
        try {
            TypedQuery<MatchConexion> query = em.createQuery(
                    "SELECT m FROM MatchConexion m WHERE " +
                    "(m.estudiante1.id = :est1 AND m.estudiante2.id = :est2) OR " +
                    "(m.estudiante1.id = :est2 AND m.estudiante2.id = :est1)", 
                    MatchConexion.class);
            
            query.setParameter("est1", estudiante1Id);
            query.setParameter("est2", estudiante2Id);

            return query.getSingleResult();
            
        } catch (NoResultException e) {
            return null; 
        }
    }
}
