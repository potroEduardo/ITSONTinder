package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * para la entidad Hobby (Lógica de Negocio).
 *
 * @author Angel
 */
import entities.Hobby;
import java.util.List;

public interface IHobbyService {

    /**
     * Busca un Hobby por su identificador único.
     */
    Hobby buscarHobbyPorId(Integer id);

    /**
     * Lista todos los hobbies disponibles en el sistema.
     */
    List<Hobby> listarTodosLosHobbies();

    /**
     * Busca un Hobby por su nombre exacto.
     */
    Hobby buscarHobbyPorNombre(String nombre);

    /**
     * Actualiza la información de un Hobby existente.
     */
    void actualizarHobby(Hobby hobby);

    /**
     * Elimina un Hobby del sistema por su ID.
     */
    void eliminarHobby(Integer id);

    /**
     * Intenta encontrar un Hobby por nombre; si no existe, lo crea y lo devuelve.
     */
    Hobby obtenerOcrearHobby(String nombre);
}