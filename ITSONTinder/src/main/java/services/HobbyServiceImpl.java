/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Hobby;
import com.example.itsontinder.dao.IHobbyDAO;
import com.example.itsontinder.dao.HobbyDAOImpl;
import com.example.itsontinder.persistence.JPAConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz de servicio IHobbyService.
 * Gestiona las transacciones y utiliza el DAO de Hobby.
 */
public class HobbyServiceImpl implements IHobbyService {

    private static final Logger LOGGER = Logger.getLogger(HobbyServiceImpl.class.getName());
    private final IHobbyDAO hobbyDAO;
    private final JPAConnection jpaConnection;

    /**
     * Constructor. Se "inyecta" la implementación del DAO.
     */
    public HobbyServiceImpl() {
        this.hobbyDAO = new HobbyDAOImpl(); // Instanciamos la implementación
        this.jpaConnection = JPAConnection.getInstance(); // Obtenemos el Singleton
    }

    @Override
    public Hobby buscarHobbyPorId(Integer id) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return hobbyDAO.buscarPorId(id, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar hobby por ID", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Hobby> listarTodosLosHobbies() {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return hobbyDAO.listar(0, em); // 0 = sin límite
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar hobbies", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Hobby buscarHobbyPorNombre(String nombre) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return hobbyDAO.buscarPorNombre(nombre, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar hobby por nombre", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void actualizarHobby(Hobby hobby) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            hobbyDAO.actualizar(hobby, em);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al actualizar hobby", e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarHobby(Integer id) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Primero buscamos la entidad para que esté gestionada por el EM
            Hobby hobby = hobbyDAO.buscarPorId(id, em);
            if (hobby != null) {
                hobbyDAO.eliminar(hobby, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al eliminar hobby", e);
        } finally {
            if (em != null) em.close();
        }
    }

    /**
     * Implementación de la lógica de negocio.
     */
    @Override
    public Hobby obtenerOcrearHobby(String nombre) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            // 1. Intentar buscar el hobby
            Hobby hobbyExistente = hobbyDAO.buscarPorNombre(nombre, em);
            
            if (hobbyExistente != null) {
                // 2. Si existe, devolverlo
                return hobbyExistente;
            } else {
                // 3. Si no existe, crearlo dentro de una transacción
                tx.begin();
                Hobby nuevoHobby = new Hobby();
                nuevoHobby.setNombre(nombre);
                hobbyDAO.crear(nuevoHobby, em);
                tx.commit();
                
                // Devolvemos el hobby recién creado (con su ID)
                return nuevoHobby;
            }
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al obtener o crear hobby", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}