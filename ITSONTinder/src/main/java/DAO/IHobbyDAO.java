package DAO;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO)
 * * @author Angel
 */
import entities.Hobby;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IHobbyDAO {

    // Métodos CRUD Genéricos

    // Metodo CRUD CREAR
    void crear(Hobby entidad, EntityManager em);

    // Metodo para buscar por ID
    Hobby buscarPorId(Integer id, EntityManager em);

    // Metodo CRUD para actualizar
    void actualizar(Hobby entidad, EntityManager em);

    // Metodo CRUD para eliminar
    void eliminar(Hobby entidad, EntityManager em);

    // Metodo para listar
    List<Hobby> listar(int limit, EntityManager em);

    // Métodos JPQL Específicos

    // Metodo para buscar por Nombre
    Hobby buscarPorNombre(String nombre, EntityManager em);
}
