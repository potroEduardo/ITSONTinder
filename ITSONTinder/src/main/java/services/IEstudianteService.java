package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * @author Angel
 */
import entities.Estudiante;
import java.util.List;

public interface IEstudianteService {

    /**
     * Registra un nuevo estudiante en el sistema.
     */
    Estudiante registrarEstudiante(Estudiante estudiante);

    /**
     * Verifica las credenciales para permitir el inicio de sesión.
     */
    Estudiante iniciarSesion(String correo, String contrasena);

    /**
     * Obtiene el perfil completo de un estudiante por su ID.
     */
    Estudiante obtenerPerfil(Integer id);

    /**
     * Actualiza la información de un estudiante existente.
     */
    void actualizarPerfil(Estudiante estudiante);

    /**
     * Elimina el perfil de un estudiante del sistema por su ID.
     */
    void eliminarPerfil(Integer id);

    /**
     * Obtiene una lista de perfiles de estudiantes disponibles para que el usuario actual interactúe.
     */
    List<Estudiante> obtenerSiguientesPerfiles(Integer estudianteActualId, int limit);
}