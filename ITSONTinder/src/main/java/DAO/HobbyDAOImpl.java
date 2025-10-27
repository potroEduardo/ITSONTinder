/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Hobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IHobbyDAO.
 * Contiene la lógica de persistencia y las consultas JPQL.
 */
public class HobbyDAOImpl implements IHobbyDAO {

    // --- Métodos CRUD Genéricos ---

    @Override
    public void crear(Hobby entidad, EntityManager em) {
        em.persist(entidad);
    }

    @Override
    public Hobby buscarPorId(Integer id, EntityManager em) {
        return em.find(Hobby.class, id);
    }

    @Override
    public void actualizar(Hobby entidad, EntityManager em) {
        em.merge(entidad);
    }

    @Override
    public void eliminar(Hobby entidad, EntityManager em) {
        if (!em.contains(entidad)) {
            Hobby managedEntity = em.merge(entidad);
            em.remove(managedEntity);
        } else {
            em.remove(entidad);
        }
    }

    @Override
    public List<Hobby> listar(int limit, EntityManager em) {
        // JPQL para seleccionar todos los hobbies
        TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h", Hobby.class);
        
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
    public Hobby buscarPorNombre(String nombre, EntityManager em) {
        try {
            // JPQL para buscar por nombre
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h WHERE h.nombre = :nombre", 
                Hobby.class);
            
            query.setParameter("nombre", nombre);
            
            // Usamos getSingleResult() porque el nombre del hobby es ÚNICO
            return query.getSingleResult();
            
        } catch (NoResultException e) {
            // Si no encuentra resultados, devuelve null
            return null; 
        }
    }
}
