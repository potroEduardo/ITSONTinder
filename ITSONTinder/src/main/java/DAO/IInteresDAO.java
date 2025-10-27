/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Interes;
import jakarta.persistence.EntityManager;

/**
 * Interfaz DAO específica para la entidad Interes.
 * Hereda los métodos CRUD de IGenericDAO y añade 
 * métodos de negocio (consultas JPQL).
 */
public interface IInteresDAO extends IGenericDAO<Interes, Integer> {

    /**
     * Busca un interés por su nombre exacto (sensible a mayúsculas).
     * Útil para evitar duplicados al crear intereses.
     * * @param nombre El nombre del interés a buscar.
     * @param em El EntityManager.
     * @return El Interes si se encuentra, o null.
     */
    Interes buscarPorNombre(String nombre, EntityManager em);
    
}