package services;

/**
 * Interfaz que define las operaciones de servicio de alto nivel
 * para la entidad MatchConexion (Lógica de Negocio de Matches).
 *
 * @author Angel
 */
import entities.Estudiante;
import entities.MatchConexion;
import java.util.List;

public interface IMatchConexionService {

    /**
     * Crea una nueva conexión de match entre dos estudiantes.
     */
    MatchConexion crearMatch(Integer estudiante1Id, Integer estudiante2Id);

    /**
     * Busca si ya existe un match entre dos estudiantes, sin importar el orden de los IDs.
     */
    boolean buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id);

    /**
     * Lista todos los perfiles de los estudiantes con los que el estudiante dado ha hecho match.
     */
    List<MatchConexion> listarMatchesDeEstudiante(Integer estudianteId);
    
    /**
     * Busca una conexión de match específica por su identificador.
     */
    MatchConexion buscarMatchPorId(Integer matchId);

    /**
     * Elimina una conexión de match específica del sistema por su ID.
     */
    void eliminarMatch(Integer matchId);
}