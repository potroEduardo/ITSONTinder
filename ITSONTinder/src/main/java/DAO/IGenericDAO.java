/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD.
 * @param <T> La clase de la Entidad.
 * @param <ID> El tipo de la clave primaria (Ej. Integer, Long, String).
 */
public interface IGenericDAO<T, ID> {

    /**
     * Persiste una nueva entidad en la base de datos.
     * @param entidad La entidad a crear.
     * @param em El EntityManager para la transacción.
     */
    void crear(T entidad, EntityManager em);

    /**
     * Busca una entidad por su clave primaria.
     * @param id La clave primaria.
     * @param em El EntityManager.
     * @return La entidad encontrada, o null.
     */
    T buscarPorId(ID id, EntityManager em);

    /**
     * Actualiza el estado de una entidad en la base de datos.
     * @param entidad La entidad a actualizar.
     * @param em El EntityManager para la transacción.
     */
    void actualizar(T entidad, EntityManager em);

    /**
     * Elimina una entidad de la base de datos.
     * @param entidad La entidad a eliminar.
     * @param em El EntityManager para la transacción.
     */
    void eliminar(T entidad, EntityManager em);

    /**
     * Lista las entidades con un límite.
     * @param limit El número máximo de resultados.
     * @param em El EntityManager.
     * @return Una lista de entidades.
     */
    List<T> listar(int limit, EntityManager em);
}

