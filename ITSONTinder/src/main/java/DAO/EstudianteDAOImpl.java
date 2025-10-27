/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IEstudianteDAO.
 * Contiene la lógica de persistencia y las consultas JPQL.
 * NO maneja transacciones, solo ejecuta las operaciones.
 */
public class EstudianteDAOImpl implements IEstudianteDAO {

    // --- Métodos CRUD Genéricos ---

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
        // Para eliminar, la entidad debe estar "manejada" (managed) por el EntityManager.
        // Si no lo está (estado "detached"), la volvemos a adjuntar con merge().
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

    // --- Métodos JPQL Específicos ---

    /**
     * Implementación de la búsqueda para el Login.
     */
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

    /**
     * Implementación de la búsqueda de perfiles para explorar.
     */
    @Override
    public List<Estudiante> buscarPerfilesParaExplorar(Integer estudianteActualId, int limit, EntityManager em) {
        
        // Esta consulta JPQL selecciona estudiantes (e) que cumplen dos condiciones:
        // 1. No son el usuario actual (e.id != :idActual)
        // 2. Su ID no está en la lista de perfiles con los que el usuario actual 
        //    (i.emisor.id) ya ha interactuado (i.receptor.id).
        
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
