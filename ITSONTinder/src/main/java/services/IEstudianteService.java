/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Estudiante;
import java.util.List;

/**
 * Interfaz para la lógica de negocio (Servicios) relacionada con Estudiantes.
 * Esta es la capa que la GUI debe llamar.
 * Maneja la lógica de transacciones.
 */
public interface IEstudianteService {

    /**
     * Registra un nuevo estudiante.
     * @param estudiante El estudiante a registrar.
     * @return El estudiante persistido (con su ID).
     */
    Estudiante registrarEstudiante(Estudiante estudiante);

    /**
     * Valida las credenciales de un estudiante.
     * @param correo Correo institucional.
     * @param contrasena Contraseña.
     * @return El Estudiante si el login es exitoso, o null.
     */
    Estudiante iniciarSesion(String correo, String contrasena);

    /**
     * Obtiene un perfil de estudiante por su ID.
     * @param id El ID del estudiante.
     * @return El estudiante, o null si no existe.
     */
    Estudiante obtenerPerfil(Integer id);

    /**
     * Actualiza la información de un perfil.
     * @param estudiante El estudiante con la información actualizada.
     */
    void actualizarPerfil(Estudiante estudiante);

    /**
     * Elimina un perfil de estudiante.
     * @param id El ID del estudiante a eliminar.
     */
    void eliminarPerfil(Integer id);

    /**
     * Obtiene una lista de perfiles para que el usuario actual explore.
     * @param estudianteActualId El ID del usuario que está explorando.
     * @param limit El número de perfiles a traer.
     * @return Una lista de Estudiantes.
     */
    List<Estudiante> obtenerSiguientesPerfiles(Integer estudianteActualId, int limit);
}