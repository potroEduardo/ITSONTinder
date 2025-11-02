package DAO;

/**
 * 
 * * @author Angel
 */
import entities.Estudiante;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IEstudianteDAO {

    
    void crear(Estudiante entidad, EntityManager em);

    
    Estudiante buscarPorId(Integer id, EntityManager em);

    
    void actualizar(Estudiante entidad, EntityManager em);

   
    void eliminar(Estudiante entidad, EntityManager em);

    
    List<Estudiante> listar(int limit, EntityManager em);

   
    Estudiante buscarPorCorreoYContrasena(String correo, String contrasena, EntityManager em);

    
    List<Estudiante> buscarPerfilesParaExplorar(Integer estudianteActualId, int limit, EntityManager em);
}
