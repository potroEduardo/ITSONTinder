package services;

/**
 * @author Angel
 */
import entities.Hobby;
import DAO.IHobbyDAO;
import DAO.HobbyDAOImpl;
import persistence.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación de la interfaz de servicio IHobbyService.
 */
public class HobbyServiceImpl implements IHobbyService {

    private static final Logger LOGGER = Logger.getLogger(HobbyServiceImpl.class.getName());
    private final IHobbyDAO hobbyDAO;


    public HobbyServiceImpl() {
        this.hobbyDAO = new HobbyDAOImpl();
 
    }

    @Override
    public Hobby buscarHobbyPorId(Integer id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
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
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            return hobbyDAO.listar(0, em); 
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar hobbies", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Hobby buscarHobbyPorNombre(String nombre) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
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
        EntityManager em = JpaUtil.getInstance().getEntityManager();
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
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
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
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            // Intentar buscar el hobby (operación de solo lectura)
            Hobby hobbyExistente = hobbyDAO.buscarPorNombre(nombre, em);
            
            if (hobbyExistente != null) {
                // Si existe, devolverlo
                return hobbyExistente;
            } else {
                // Si no existe, crearlo dentro de una transacción
                tx.begin();
                Hobby nuevoHobby = new Hobby();
                nuevoHobby.setNombre(nombre);
                hobbyDAO.crear(nuevoHobby, em);
                tx.commit();
                
                // Se devuelve el hobby recién creado
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