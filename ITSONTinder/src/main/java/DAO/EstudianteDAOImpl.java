
package DAO;

/**
 *
 * @author Angel
 */
import entities.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class EstudianteDAOImpl implements IEstudianteDAO {

   

    @Override
    public void crear(Estudiante entidad, EntityManager em) {
        em.persist(entidad);
    }

    @Override
    public Estudiante buscarPorId(Integer id, EntityManager em) {
        return em.find(Estudiante.class, id);
    }

    @Override
    public void actualizar(Estudiante entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(Estudiante entidad, EntityManager em) {
        
        if (!em.contains(entidad)) {
            Estudiante managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<Estudiante> listar(int limit, EntityManager em) {
        // JPQL para seleccionar todos los estudiantes
        TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e", Estudiante.class);
        
        // Aplicamos el límite de resultados
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }

    
    @Override
    public Estudiante buscarPorCorreoYContrasena(String correo, String contrasena, EntityManager em) {
        try {
            // JPQL para buscar por correo y contraseña
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.correoInstitucional = :correo AND e.contrasena = :pass", 
                Estudiante.class);
            
            // Asignamos los parámetros
            query.setParameter("correo", correo);
            query.setParameter("pass", contrasena);
            
            // Usamos getSingleResult() porque el correo es ÚNICO
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            // Si no encuentra resultados, lanza NoResultException.
            // Capturamos la excepción y devolvemos null, indicando que el login falló.
            return null; 
        }
    }

    
    @Override
    public List<Estudiante> buscarPerfilesParaExplorar(Integer estudianteActualId, int limit, EntityManager em) {
        
        
        String jpql = "SELECT e FROM Estudiante e " +
                      "WHERE e.id != :idActual AND e.id NOT IN (" +
                      "  SELECT i.receptor.id FROM Interaccion i " +
                      "  WHERE i.emisor.id = :idActual" +
                      ")";
        
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("idActual", estudianteActualId);
        
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }
    }
