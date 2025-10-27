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
import com.example.itsontinder.MatchConexion;
import java.util.List;

/**
 * Interfaz para la capa de servicio de la entidad MatchConexion.
 * Define la lógica de negocio para crear y gestionar matches.
 */
public interface IMatchConexionService {

    /**
     * Lógica de negocio principal: Crea un nuevo match entre dos estudiantes.
     * Es llamado por InteraccionServiceImpl cuando se detecta un "Me gusta" mutuo.
     *
     * @param estudiante1Id ID del primer estudiante.
     * @param estudiante2Id ID del segundo estudiante.
     * @return El MatchConexion recién creado.
     */
    MatchConexion crearMatch(Integer estudiante1Id, Integer estudiante2Id);

    /**
     * Busca si ya existe un match entre dos estudiantes, sin importar el orden.
     * (Ej: busca A-B y B-A).
     *
     * @param estudiante1Id ID del primer estudiante.
     * @param estudiante2Id ID del segundo estudiante.
     * @return true si ya existe un match, false si no.
     */
    boolean buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id);

    /**
     * Obtiene la lista de todos los matches de un estudiante específico.
     *
     * @param estudianteId El ID del estudiante del cual se quieren ver los matches.
     * @return Una lista de Estudiantes (las personas con las que hizo match).
     */
    List<Estudiante> listarMatchesDeEstudiante(Integer estudianteId);

    /**
     * Busca un match por su ID de BBDD.
     * @param matchId El ID del match.
     * @return El MatchConexion, o null.
     */
    MatchConexion buscarMatchPorId(Integer matchId);
    
    /**
     * Elimina un match por su ID.
     *
     * @param matchId El ID del match a eliminar.
     */
    void eliminarMatch(Integer matchId);
}
