package DAO;

import entities.MatchConexion;
import jakarta.persistence.EntityManager;
import java.util.List;

public interface IMatchConexionDAO {

    // Métodos CRUD Genéricos

    // Metodo para CREAR
    void crear(MatchConexion entidad, EntityManager em);

    // Metodo para Buscar Por ID
    MatchConexion buscarPorId(Integer id, EntityManager em);

    // Metodo para Actualizar
    void actualizar(MatchConexion entidad, EntityManager em);

    // Metodo para eliminar
    void eliminar(MatchConexion entidad, EntityManager em);

    // Metodo para listar
    List<MatchConexion> listar(int limit, EntityManager em);

    // Metodo para listar Matches por Estudiante
    List<MatchConexion> listarMatchesPorEstudiante(Integer estudianteId, EntityManager em);

    // Conexcion para buscar match existente
    MatchConexion buscarMatchExistente(Integer estudiante1Id, Integer estudiante2Id, EntityManager em);
}

