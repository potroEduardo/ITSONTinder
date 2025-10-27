/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Interaccion;
import com.example.itsontinder.TipoInteraccion;

/**
 * Interfaz para la capa de servicio de la entidad Interaccion.
 * Define la lógica de negocio para registrar "swipes" y crear matches.
 */
public interface IInteraccionService {

    /**
     * Lógica de negocio principal: Registra la acción de un estudiante (emisor)
     * sobre otro (receptor).
     *
     * Si la acción es "Me gusta" y el receptor ya había dado "Me gusta" al emisor,
     * este método también se encarga de crear el Match.
     *
     * @param emisorId El ID del estudiante que realiza la acción.
     * @param receptorId El ID del estudiante que recibe la acción.
     * @param tipo El tipo de acción (ME_GUSTA, NO_ME_INTERESA).
     * @return true si se ha creado un NUEVO match, false en cualquier otro caso.
     */
    boolean registrarAccion(Integer emisorId, Integer receptorId, TipoInteraccion tipo);

    /**
     * Busca si ya existe una interacción previa entre dos estudiantes.
     *
     * @param emisorId El ID del estudiante que realizó la acción.
     * @param receptorId El ID del estudiante que recibió la acción.
     * @return La Interaccion si existe, o null.
     */
    Interaccion buscarInteraccionExistente(Integer emisorId, Integer receptorId);

    /**
     * Actualiza una acción existente. (Ej: cambiar de "No me interesa" a "Me gusta").
     *
     * @param emisorId El ID del emisor.
     * @param receptorId El ID del receptor.
     * @param nuevoTipo El nuevo tipo de interacción.
     */
    void actualizarAccion(Integer emisorId, Integer receptorId, TipoInteraccion nuevoTipo);

    /**
     * Elimina una acción (poco común, pero útil para CRUD).
     *
     * @param emisorId El ID del emisor.
     * @param receptorId El ID del receptor.
     */
    void eliminarAccion(Integer emisorId, Integer receptorId);
}