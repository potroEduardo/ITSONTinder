package DAO;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO)
 * * @author Angel
 */
import entities.Interes;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IInteresDAO {

    // Métodos CRUD Genéricos

    // Metodo CRUD CREAR
    void crear(Interes entidad, EntityManager em);

    // Metodo para Buscar por ID
    Interes buscarPorId(Integer id, EntityManager em);

    // Metodo para actualizar
    void actualizar(Interes entidad, EntityManager em);

    // Metodo para Eliminar
    void eliminar(Interes entidad, EntityManager em);

    // Metodo para listar
    List<Interes> listar(int limit, EntityManager em);

    // Métodos JPQL Específicos

    // Metodo para buscar por Nombre
    Interes buscarPorNombre(String nombre, EntityManager em);
}