/*package services;

/**
 *
 * @author Angel
 */
import entities.Estudiante;
import DAO.EstudianteDAOImpl;
import DAO.IEstudianteDAO;
import persistence.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import services.IEstudianteService;

/**
 * Implementación de IEstudianteService
 */
public class EstudianteServiceImpl implements IEstudianteService {

    // El servicio utiliza una implementación del DAO
    private IEstudianteDAO estudianteDAO;

    public EstudianteServiceImpl() {
        this.estudianteDAO = new EstudianteDAOImpl();
    }

    @Override
    public Estudiante registrarEstudiante(Estudiante estudiante) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            estudianteDAO.crear(estudiante, em);
            em.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al registrar estudiante: " + e.getMessage());
            e.printStackTrace();
            return null; // O lanzar una excepción personalizada
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante iniciarSesion(String correo, String contrasena) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            // "iniciarSesion" es una operación de solo lectura,
            // no es estrictamente necesario usar una transacción,
            // pero es buena práctica para la consistencia.
            return estudianteDAO.buscarPorCorreoYContrasena(correo, contrasena, em);
        } catch (Exception e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante obtenerPerfil(Integer id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            return estudianteDAO.buscarPorId(id, em);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarPerfil(Estudiante estudiante) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            estudianteDAO.actualizar(estudiante, em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al actualizar perfil: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarPerfil(Integer id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            
            Estudiante estudiante = estudianteDAO.buscarPorId(id, em);
            if (estudiante != null) {
                estudianteDAO.eliminar(estudiante, em);
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar perfil: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerSiguientesPerfiles(Integer estudianteActualId, int limit) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            return estudianteDAO.buscarPerfilesParaExplorar(estudianteActualId, limit, em);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}