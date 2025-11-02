package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * para la entidad Interes (Lógica de Negocio).
 *
 * @author Angel
 */
import entities.Interes;
import java.util.List;

public interface IInteresService {

    /**
     * Busca un Interés por su identificador único.
     */
    Interes buscarInteresPorId(Integer id);

    /**
     */
    List<Interes> listarTodosLosIntereses();

    /**
     * Busca un Interés por su nombre exacto.
     */
    Interes buscarInteresPorNombre(String nombre);

    /**
     * Actualiza la información de un Interés existente.

     */
    void actualizarInteres(Interes interes);

    /**
     * Elimina un Interés del sistema por su ID.

     */
    void eliminarInteres(Integer id);

    /**
     * Intenta encontrar un Interés por nombre; si no existe, lo crea y lo devuelve.
     */
    Interes obtenerOcrearInteres(String nombre);
}