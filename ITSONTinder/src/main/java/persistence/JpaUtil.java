package persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de utilidad (Singleton) para gestionar el acceso a JPA.
 * * @author Angel
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "ITSONTinderPU";

    private static EntityManagerFactory emf;
    
    
    
    private JpaUtil() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            System.out.println("EntityManagerFactory inicializado con éxito.");
        } catch (Exception e) {
            System.err.println("FATAL: Error al inicializar el EntityManagerFactory ('" + PERSISTENCE_UNIT_NAME + "').");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e); 
        }
    }

    private static class SingletonHelper {
        private static final JpaUtil INSTANCE = new JpaUtil();
    }

    /**
     * Devuelve la instancia única de JpaUtil (Singleton).
     */
    public static JpaUtil getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    // Métodos de Acceso a Recursos

    /**
     * Provee un nuevo EntityManager. 
     */
    public EntityManager getEntityManager() {
        if (emf == null) {
             throw new IllegalStateException("EntityManagerFactory es nulo. La conexión no se inicializó correctamente.");
        }
        // Crea una nueva sesión (EntityManager)
        return emf.createEntityManager();
    }

    /**
     * Cierra el EntityManagerFactory, liberando todos los recursos.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
            System.out.println("EntityManagerFactory cerrado.");
        }
    }
}
