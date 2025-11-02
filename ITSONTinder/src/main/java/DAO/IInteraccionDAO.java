package DAO;

/**
 *
 * @author Angel
 */
import entities.Interaccion;
import entities.InteraccionPK;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IInteraccionDAO {

    //Métodos CRUD Genéricos

    // Metodo CRUD CREAR
    void crear(Interaccion entidad, EntityManager em);

    // Metodo para Buscar por ID
    Interaccion buscarPorId(InteraccionPK id, EntityManager em);

    // Metodo CRUD Actualizar
    void actualizar(Interaccion entidad, EntityManager em);

    // Metodo CRUD Eliminar
    void eliminar(Interaccion entidad, EntityManager em);

    // Metodo para listar
    List<Interaccion> listar(int limit, EntityManager em);

    // Métodos JPQL Específicos 

    // Metodo para buscar interaccion
    Interaccion buscarInteraccion(Integer emisorId, Integer receptorId, EntityManager em);
}
