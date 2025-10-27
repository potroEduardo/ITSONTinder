/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Interaccion;
import com.example.itsontinder.InteraccionPK;
import jakarta.persistence.EntityManager;

/**
 * Interfaz DAO específica para la entidad Interaccion.
 * Hereda los métodos CRUD de IGenericDAO.
 * El tipo de ID es la clave compuesta InteraccionPK.
 */
public interface IInteraccionDAO extends IGenericDAO<Interaccion, InteraccionPK> {

    /**
     * Busca una interacción específica enviada por un emisor a un receptor.
     * Útil para verificar si el emisor ya interactuó con el receptor.
     * * @param emisorId El ID del estudiante que envió la interacción.
     * @param receptorId El ID del estudiante que recibió la interacción.
     * @param em El EntityManager.
     * @return La Interaccion si se encuentra, o null.
     */
    Interaccion buscarInteraccion(Integer emisorId, Integer receptorId, EntityManager em);

}
