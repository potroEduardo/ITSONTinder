/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Hobby;
import jakarta.persistence.EntityManager;

/**
 * Interfaz DAO específica para la entidad Hobby.
 * Hereda los métodos CRUD de IGenericDAO y añade 
 * métodos de negocio (consultas JPQL).
 */
public interface IHobbyDAO extends IGenericDAO<Hobby, Integer> {

    /**
     * Busca un hobby por su nombre exacto (sensible a mayúsculas).
     * Útil para evitar duplicados al crear hobbies.
     * * @param nombre El nombre del hobby a buscar.
     * @param em El EntityManager.
     * @return El Hobby si se encuentra, o null.
     */
    Hobby buscarPorNombre(String nombre, EntityManager em);
    
}
