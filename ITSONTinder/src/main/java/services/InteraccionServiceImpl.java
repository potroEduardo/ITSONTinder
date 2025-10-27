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
import com.example.itsontinder.Interaccion;
import com.example.itsontinder.InteraccionPK;
import com.example.itsontinder.TipoInteraccion;
import com.example.itsontinder.dao.IInteraccionDAO;
import com.example.itsontinder.dao.InteraccionDAOImpl;
import com.example.itsontinder.persistence.JPAConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación del servicio de Interaccion.
 * Contiene la lógica para registrar "swipes" y detectar matches.
 */
public class InteraccionServiceImpl implements IInteraccionService {

    private static final Logger LOGGER = Logger.getLogger(InteraccionServiceImpl.class.getName());
    private final IInteraccionDAO interaccionDAO;
    private final JPAConnection jpaConnection;
    
    // Dependencia clave: El servicio de Interaccion necesita llamar al servicio de Match
    private final IMatchConexionService matchService;

    public InteraccionServiceImpl() {
        this.interaccionDAO = new InteraccionDAOImpl();
        this.jpaConnection = JPAConnection.getInstance();
        // Asumimos que MatchConexionServiceImpl ya existe (lo crearemos después)
        this.matchService = new MatchConexionServiceImpl(); 
    }

    @Override
    public boolean registrarAccion(Integer emisorId, Integer receptorId, TipoInteraccion tipo) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            // 1. Guardar la acción principal (A -> B)
            tx.begin();
            
            InteraccionPK pk = new InteraccionPK(emisorId, receptorId);
            
            // Usamos getReference para no golpear la BD si no es necesario
            Estudiante emisor = em.getReference(Estudiante.class, emisorId);
            Estudiante receptor = em.getReference(Estudiante.class, receptorId);

            Interaccion nuevaAccion = new Interaccion();
            nuevaAccion.setId(pk);
            nuevaAccion.setEmisor(emisor);
            nuevaAccion.setReceptor(receptor);
            nuevaAccion.setTipo(tipo);
            
            // Usamos actualizar (merge) en lugar de crear (persist)
            // por si el usuario cambia de "No me interesa" a "Me gusta".
            interaccionDAO.actualizar(nuevaAccion, em); 
            
            tx.commit();

            // 2. Lógica de Match (solo si fue "Me gusta")
            if (tipo == TipoInteraccion.ME_GUSTA) {
                // 3. Buscar la acción inversa (B -> A)
                // Esta llamada abre su propio EntityManager, lo cual está bien.
                Interaccion accionInversa = this.buscarInteraccionExistente(receptorId, emisorId);

                if (accionInversa != null && accionInversa.getTipo() == TipoInteraccion.ME_GUSTA) {
                    // ¡ES UN MATCH!
                    // 4. Verificar si el match ya existe (para evitar duplicados)
                    boolean matchYaExiste = matchService.buscarMatchExistente(emisorId, receptorId);
                    
                    if (!matchYaExiste) {
                        // 5. Crear el match (esta llamada maneja su propia transacción)
                        matchService.crearMatch(emisorId, receptorId);
                        return true; // ¡Se creó un nuevo match!
                    }
                }
            }
            return false; // No se creó un nuevo match
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al registrar acción", e);
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Interaccion buscarInteraccionExistente(Integer emisorId, Integer receptorId) {
        EntityManager em = jpaConnection.createEntityManager();
        try {
            return interaccionDAO.buscarInteraccion(emisorId, receptorId, em);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar interacción", e);
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void actualizarAccion(Integer emisorId, Integer receptorId, TipoInteraccion nuevoTipo) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            InteraccionPK pk = new InteraccionPK(emisorId, receptorId);
            Interaccion interaccion = interaccionDAO.buscarPorId(pk, em);
            if (interaccion != null) {
                interaccion.setTipo(nuevoTipo);
                interaccionDAO.actualizar(interaccion, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al actualizar acción", e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void eliminarAccion(Integer emisorId, Integer receptorId) {
        EntityManager em = jpaConnection.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            InteraccionPK pk = new InteraccionPK(emisorId, receptorId);
            Interaccion interaccion = interaccionDAO.buscarPorId(pk, em);
            if (interaccion != null) {
                interaccionDAO.eliminar(interaccion, em);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al eliminar acción", e);
        } finally {
            if (em != null) em.close();
        }
    }
}