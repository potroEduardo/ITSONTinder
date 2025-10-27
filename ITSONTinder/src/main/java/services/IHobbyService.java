/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Hobby;
import java.util.List;

/**
 * Interfaz para la capa de servicio de la entidad Hobby.
 * Define la lógica de negocio y gestiona las transacciones.
 */
public interface IHobbyService {

    /**
     * Busca un hobby por su ID.
     * @param id El ID del hobby.
     * @return El Hobby encontrado, o null.
     */
    Hobby buscarHobbyPorId(Integer id);

    /**
     * Obtiene una lista de todos los hobbies.
     * @return Lista de Hobbies.
     */
    List<Hobby> listarTodosLosHobbies();

    /**
     * Busca un hobby por su nombre exacto.
     * @param nombre El nombre del hobby a buscar.
     * @return El Hobby encontrado, o null.
     */
    Hobby buscarHobbyPorNombre(String nombre);

    /**
     * Actualiza un hobby existente en la base de datos.
     * @param hobby El hobby con los datos actualizados.
     */
    void actualizarHobby(Hobby hobby);

    /**
     * Elimina un hobby por su ID.
     * @param id El ID del hobby a eliminar.
     */
    void eliminarHobby(Integer id);
    
    /**
     * Lógica de negocio principal: Busca un hobby por nombre.
     * Si no existe, lo crea y lo guarda.
     * Si ya existe, lo devuelve.
     * @param nombre El nombre del hobby (ej: "Videojuegos").
     * @return El Hobby (ya sea existente o recién creado).
     */
    Hobby obtenerOcrearHobby(String nombre);
    
}