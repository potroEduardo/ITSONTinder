/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Interes;
import java.util.List;

/**
 * Interfaz para la capa de servicio de la entidad Interes.
 * Define la lógica de negocio y gestiona las transacciones.
 */
public interface IInteresService {

    /**
     * Busca un interés por su ID.
     * @param id El ID del interés.
     * @return El Interes encontrado, o null.
     */
    Interes buscarInteresPorId(Integer id);

    /**
     * Obtiene una lista de todos los intereses.
     * @return Lista de Interes.
     */
    List<Interes> listarTodosLosIntereses();

    /**
     * Busca un interés por su nombre exacto.
     * @param nombre El nombre del interés a buscar.
     * @return El Interes encontrado, o null.
     */
    Interes buscarInteresPorNombre(String nombre);

    /**
     * Actualiza un interés existente en la base de datos.
     * @param interes El interés con los datos actualizados.
     */
    void actualizarInteres(Interes interes);

    /**
     * Elimina un interés por su ID.
     * @param id El ID del interés a eliminar.
     */
    void eliminarInteres(Integer id);
    
    /**
     * Lógica de negocio principal: Busca un interés por nombre.
     * Si no existe, lo crea y lo guarda.
     * Si ya existe, lo devuelve.
     * @param nombre El nombre del interés (ej: "Tecnología").
     * @return El Interes (ya sea existente o recién creado).
     */
    Interes obtenerOcrearInteres(String nombre);
    
}