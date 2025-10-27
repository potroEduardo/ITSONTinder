/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Estudiante;
import com.example.itsontinder.MatchConexion;
import com.example.itsontinder.Mensaje;
import com.example.itsontinder.dao.IMensajeDAO;
import com.example.itsontinder.dao.MensajeDAOImpl;
import com.example.itsontinder.persistence.JPAConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio de Mensaje.
 * Gestiona el envío y la carga de chats.
 */
public class MensajeServiceImpl implements IMensajeService {

    private static final Logger LOGGER = Logger.getLogger(MensajeServiceImpl.class.getName());
    private final IMensajeDAO mensajeDAO;
    private final JPAConnection jpaConnection;

    public MensajeServiceImpl() {
        this.mensajeDAO = new MensajeDAOImpl();
        this.jpaConnection = JPAConnection.getInstance();
    }

    @Override
    public Mensaje enviarMensaje(Integer matchId, Integer emisorId, String contenido) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            
            Mensaje nuevoMensaje = new Mensaje();
            nuevoMensaje.setContenido(contenido);
            
            // Usamos getReference para crear "proxies" eficientes
            // No necesitamos consultar la info del match o del emisor,
            // solo necesitamos la referencia para la clave foránea.
            MatchConexion match = em.getReference(MatchConexion.class, matchId);
            Estudiante emisor = em.getReference(Estudiante.class, emisorId);
            
            nuevoMensaje.setMatch(match);
            nuevoMensaje.setEmisor(emisor);
            
            mensajeDAO.crear(nuevoMensaje, em);
            
            tx.commit();
            
            // Devolvemos el mensaje con su ID y timestamp (generados por la BD)
            return nuevoMensaje;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al enviar mensaje", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Mensaje> cargarHistorialDeChat(Integer matchId) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            // La lógica de la consulta JPQL está en el DAO
            return mensajeDAO.listarMensajesPorMatch(matchId, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar historial de chat", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarMensaje(Integer mensajeId) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Mensaje mensaje = mensajeDAO.buscarPorId(mensajeId, em);
            if (mensaje != null) {
                mensajeDAO.eliminar(mensaje, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al eliminar mensaje", e);
        } finally {
            if (em != null) em.close();
        }
    }
    
    @Override
    public Mensaje buscarMensajePorId(Integer mensajeId) {
         EntityManager em = jpaConnection.createEntityManager();
        try {
            return mensajeDAO.buscarPorId(mensajeId, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar mensaje por ID", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
}