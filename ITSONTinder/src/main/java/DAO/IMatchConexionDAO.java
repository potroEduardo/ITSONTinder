package DAO;

/**
 * @author Angel
 */
import entities.Estudiante; 
import entities.MatchConexion;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IMatchConexionDAO {

    // Métodos CRUD Genéricos
    void crear(MatchConexion entidad, EntityManager em);
    MatchConexion buscarPorId(Integer id, EntityManager em);
    void actualizar(MatchConexion entidad, EntityManager em);
    void eliminar(MatchConexion entidad, EntityManager em);
    List<MatchConexion> listar(int limit, EntityManager em);

    // Métodos JPQL Específicos
    
    /**
     * Busca y devuelve todos los matches (conexiones) de un estudiante.
     * @param estudianteId El ID del estudiante del cual se quieren buscar los matches.
     * @param em El EntityManager para la consulta.
     * @return Una lista de MatchConexion.
     */
    List<MatchConexion> listarMatchesPorEstudiante(Integer estudianteId, EntityManager em);

    /**
     * Busca si ya existe un match entre dos estudiantes.
     * @param estudiante1Id ID del primer estudiante.
     * @param estudiante2Id ID del segundo estudiante.
     * @param em El EntityManager para la consulta.
     * @return El MatchConexion si existe, null si no.
     */
    MatchConexion buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id, EntityManager em);
}

