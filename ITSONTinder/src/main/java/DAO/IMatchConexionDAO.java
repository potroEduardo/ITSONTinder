/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author Laptop
 */
import com.example.itsontinder.MatchConexion;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO específica para la entidad MatchConexion.
 * Hereda los métodos CRUD de IGenericDAO.
 */
public interface IMatchConexionDAO extends IGenericDAO<MatchConexion, Integer> {

    /**
     * Busca todos los matches en los que un estudiante participa.
     * Útil para la ventana de "Mis Matches".
     *
     * @param estudianteId El ID del estudiante.
     * @param em El EntityManager.
     * @return Una lista de MatchConexion.
     */
    List<MatchConexion> listarMatchesPorEstudiante(Integer estudianteId, EntityManager em);
    
    /**
     * Verifica si ya existe un match entre dos estudiantes, sin importar el orden.
     * Útil para la lógica de negocio al crear un match.
     *
     * @param estudiante1Id ID del primer estudiante.
     * @param estudiante2Id ID del segundo estudiante.
     * @param em El EntityManager.
     * @return El MatchConexion si existe, o null.
     */
    MatchConexion buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id, EntityManager em);

}

