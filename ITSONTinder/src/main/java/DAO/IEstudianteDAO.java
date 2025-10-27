/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.Estudiante;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO específica para la entidad Estudiante.
 * Hereda los métodos CRUD de IGenericDAO y añade 
 * métodos de negocio (consultas JPQL).
 */
public interface IEstudianteDAO extends IGenericDAO<Estudiante, Integer> {

    /**
     * Busca un estudiante por su correo y contraseña (para Login).
     * @param correo Correo institucional.
     * @param contrasena Contraseña.
     * @param em El EntityManager.
     * @return El Estudiante si se encuentra, o null.
     */
    Estudiante buscarPorCorreoYContrasena(String correo, String contrasena, EntityManager em);

    /**
     * Busca perfiles de otros estudiantes que el usuario actual NO haya
     * interactuado (Me gusta / No me interesa).
     * @param estudianteActualId El ID del usuario que está explorando.
     * @param limit El número de perfiles a traer.
     * @param em El EntityManager.
     * @return Una lista de Estudiantes para explorar.
     */
    List<Estudiante> buscarPerfilesParaExplorar(Integer estudianteActualId, int limit, EntityManager em);
}
