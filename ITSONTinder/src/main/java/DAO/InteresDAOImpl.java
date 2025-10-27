/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Interes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IInteresDAO.
 * Contiene la lógica de persistencia y las consultas JPQL.
 */
public class InteresDAOImpl implements IInteresDAO {

    // --- Métodos CRUD Genéricos ---

    @Override
    public void crear(Interes entidad, EntityManager em) {
        em.persist(entidad);
    }

    @Override
    public Interes buscarPorId(Integer id, EntityManager em) {
        return em.find(Interes.class, id);
    }

    @Override
    public void actualizar(Interes entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(Interes entidad, EntityManager em) {
        if (!em.contains(entidad)) {
            Interes managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<Interes> listar(int limit, EntityManager em) {
        // JPQL para seleccionar todos los intereses
        TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i", Interes.class);
        
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        
        return query.getResultList();
    }

    // --- Métodos JPQL Específicos ---

    /**
     * Implementación de la búsqueda por nombre.
     */
    @Override
    public Interes buscarPorNombre(String nombre, EntityManager em) {
        try {
            // JPQL para buscar por nombre
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i WHERE i.nombre = :nombre", 
                Interes.class);
            
            query.setParameter("nombre", nombre);
            
            // Usamos getSingleResult() porque el nombre del interés es ÚNICO
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            // Si no encuentra resultados, devuelve null
            return null; 
        }
    }
}