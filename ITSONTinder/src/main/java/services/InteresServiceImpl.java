/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
public class InteresServiceImpl {
    
    import com.example.itsontinder.Interes;
import com.example.itsontinder.dao.IInteresDAO;
import com.example.itsontinder.dao.InteresDAOImpl;
import com.example.itsontinder.persistence.JPAConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz de servicio IInteresService.
 * Gestiona las transacciones y utiliza el DAO de Interes.
 */
public class InteresServiceImpl implements IInteresService {

    private static final Logger LOGGER = Logger.getLogger(InteresServiceImpl.class.getName());
    private final IInteresDAO interesDAO;
    private final JPAConnection jpaConnection;

    /**
     * Constructor. Se "inyecta" la implementación del DAO.
     */
    public InteresServiceImpl() {
        this.interesDAO = new InteresDAOImpl(); // Instanciamos la implementación
        this.jpaConnection = JPAConnection.getInstance(); // Obtenemos el Singleton
    }

    @Override
    public Interes buscarInteresPorId(Integer id) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return interesDAO.buscarPorId(id, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar interés por ID", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Interes> listarTodosLosIntereses() {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return interesDAO.listar(0, em); // 0 = sin límite
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar intereses", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Interes buscarInteresPorNombre(String nombre) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return interesDAO.buscarPorNombre(nombre, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar interés por nombre", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void actualizarInteres(Interes interes) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            interesDAO.actualizar(interes, em);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al actualizar interés", e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarInteres(Integer id) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Primero buscamos la entidad para que esté gestionada por el EM
            Interes interes = interesDAO.buscarPorId(id, em);
            if (interes != null) {
                interesDAO.eliminar(interes, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al eliminar interés", e);
        } finally {
            if (em != null) em.close();
        }
    }

    /**
     * Implementación de la lógica de negocio.
     */
    @Override
    public Interes obtenerOcrearInteres(String nombre) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            // 1. Intentar buscar el interés
            Interes interesExistente = interesDAO.buscarPorNombre(nombre, em);
            
            if (interesExistente != null) {
                // 2. Si existe, devolverlo
                return interesExistente;
            } else {
                // 3. Si no existe, crearlo dentro de una transacción
                tx.begin();
                Interes nuevoInteres = new Interes();
                nuevoInteres.setNombre(nombre);
                interesDAO.crear(nuevoInteres, em);
                tx.commit();
                
                // Devolvemos el interés recién creado (con su ID)
                return nuevoInteres;
            }
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al obtener o crear interés", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}
}
