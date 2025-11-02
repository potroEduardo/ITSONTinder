package services;

/**
 *
 * @author Angel
 */
import entities.Estudiante;
import entities.MatchConexion;
import DAO.IMatchConexionDAO;
import DAO.MatchConexionDAOImpl;
import persistence.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio de MatchConexion.
 */
public class MatchConexionServiceImpl implements IMatchConexionService {

    private static final Logger LOGGER = Logger.getLogger(MatchConexionServiceImpl.class.getName());
    private final IMatchConexionDAO matchDAO;
    private final JpaUtil jpaConnection;

    public MatchConexionServiceImpl() {
        this.matchDAO = new MatchConexionDAOImpl();
        this.jpaConnection = JpaUtil.getInstance();
    }

    @Override
    public MatchConexion crearMatch(Integer estudiante1Id, Integer estudiante2Id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Estudiante est1 = em.getReference(Estudiante.class, estudiante1Id);
            Estudiante est2 = em.getReference(Estudiante.class, estudiante2Id);

            MatchConexion nuevoMatch = new MatchConexion();
            nuevoMatch.setEstudiante1(est1);
            nuevoMatch.setEstudiante2(est2);

            matchDAO.crear(nuevoMatch, em);
            
            tx.commit();
            LOGGER.log(Level.INFO, "Match creado exitosamente entre {0} y {1}", new Object[]{estudiante1Id, estudiante2Id});
            return nuevoMatch;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al crear match", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    // CUIDADO AQUI NO ESTOY SEGURO SI ESTA BIEN!!
    public boolean buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            MatchConexion match = matchDAO.buscarMatchExistente(estudiante1Id, estudiante2Id, em);
            return (match != null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar match existente", e);
            return false; // Asumir que no existe si hay error
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<MatchConexion> listarMatchesDeEstudiante(Integer estudianteId) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            return matchDAO.listarMatchesPorEstudiante(estudianteId, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar matches", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
    
    @Override
    public MatchConexion buscarMatchPorId(Integer matchId) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            return matchDAO.buscarPorId(matchId, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar match por ID", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarMatch(Integer matchId) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MatchConexion match = matchDAO.buscarPorId(matchId, em);
            if (match != null) {
                matchDAO.eliminar(match, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al eliminar match", e);
        } finally {
            if (em != null) em.close();
        }
    }
}