package DAO;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO)
 *
 * @author Angel
 */
import entities.Mensaje;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IMensajeDAO {

    // Métodos CRUD Genéricos

    // Metodo CRUD CREAR
    void crear(Mensaje entidad, EntityManager em);

    // Buscar por ID
    Mensaje buscarPorId(Integer id, EntityManager em);

    // Metodo para actualizar CRUD
    void actualizar(Mensaje entidad, EntityManager em);

    // Metodo para eliminar CRUD
    void eliminar(Mensaje entidad, EntityManager em);

    // Metodo para Listar
    List<Mensaje> listar(int limit, EntityManager em);

    // Métodos JPQL Específicos

    // Metodo para listar mensaje por Match
    List<Mensaje> listarMensajesPorMatch(Integer matchId, EntityManager em);
}
