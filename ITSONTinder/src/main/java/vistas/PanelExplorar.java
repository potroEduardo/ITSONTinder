package vistas;

import DAO.EstudianteDAOImpl;
import DAO.IEstudianteDAO;
import DAO.IInteraccionDAO;
import DAO.InteraccionDAOImpl;
import DAO.IMatchConexionDAO;
import DAO.MatchConexionDAOImpl;
import entities.Estudiante;
import entities.Interaccion;
import entities.InteraccionPK; 
import entities.MatchConexion;
import entities.TipoInteraccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery; 
import persistence.JpaUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PnlExplorar (Vistas desde Cero)
 * @author Angel
 */
public class PanelExplorar extends JPanel {

    // --- 1. Componentes de UI (Sin cambios) ---
    private JLabel lblNombreEdad;
    private JLabel lblCarrera;
    private JLabel lblFotoURL;
    private JTextArea txtDescripcion;
    private JButton btnDislike;
    private JButton btnLike;
    private JPanel panelPerfil;
    private JPanel panelBotones;

    //Datos y Lógica
    private Estudiante estudianteActual;
    private List<Estudiante> perfilesPorMostrar;
    private int perfilActualIndex = 0;

    private IEstudianteDAO estudianteDAO;
    private IInteraccionDAO interaccionDAO;
    private IMatchConexionDAO matchConexionDAO;

    /**
     * Constructor del panel de exploración.
     * @param estudianteActual El estudiante que ha iniciado sesión.
     */
    public PanelExplorar(Estudiante estudianteActual) {
        this.estudianteActual = estudianteActual;

        // Inicializar DAOs
        this.estudianteDAO = new EstudianteDAOImpl();
        this.interaccionDAO = new InteraccionDAOImpl();
        this.matchConexionDAO = new MatchConexionDAOImpl();

        // Configurar el layout de este panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Construir UI
        
        //Panel Central
        panelPerfil = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblNombreEdad = new JLabel("Cargando...");
        lblNombreEdad.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPerfil.add(lblNombreEdad, gbc);

        gbc.gridy = 1;
        panelPerfil.add(new JLabel("Carrera:"), gbc);
        lblCarrera = new JLabel("");
        gbc.gridy = 2;
        panelPerfil.add(lblCarrera, gbc);

        gbc.gridy = 3;
        panelPerfil.add(new JLabel("Foto URL:"), gbc);
        lblFotoURL = new JLabel("");
        gbc.gridy = 4;
        panelPerfil.add(lblFotoURL, gbc);

        gbc.gridy = 5;
        panelPerfil.add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        gbc.gridy = 6; gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelPerfil.add(new JScrollPane(txtDescripcion), gbc);
        
        this.add(panelPerfil, BorderLayout.CENTER);

        //Panel Sur
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnDislike = new JButton("Dislike (❌)");
        btnLike = new JButton("Like (❤️)");
        panelBotones.add(btnDislike);
        panelBotones.add(btnLike);
        
        this.add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnDislike.addActionListener(e -> registrarInteraccion(TipoInteraccion.NO_ME_INTERESA));
        btnLike.addActionListener(e -> registrarInteraccion(TipoInteraccion.ME_GUSTA));

        //Cargar Datos Iniciales
        cargarPerfiles();
    }

    /**
     * Carga la lista de perfiles a explorar desde la BD.
     */
    private void cargarPerfiles() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            perfilesPorMostrar = estudianteDAO.buscarPerfilesParaExplorar(estudianteActual.getId(), 10, em);
            
            perfilActualIndex = 0;
            if (perfilesPorMostrar.isEmpty()) {
                mostrarSinPerfiles();
            } else {
                mostrarSiguientePerfil();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar perfiles: " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }

    /**
     * Muestra el siguiente perfil de la lista en la UI.
     */
    private void mostrarSiguientePerfil() {
        if (perfilActualIndex < perfilesPorMostrar.size()) {
            Estudiante perfil = perfilesPorMostrar.get(perfilActualIndex);
            
            lblNombreEdad.setText(perfil.getNombre() + ", " + perfil.getEdad());
            lblCarrera.setText(perfil.getCarrera());
            lblFotoURL.setText(perfil.getFotoPerfilURL());
            txtDescripcion.setText(perfil.getDescripcion());
        } else {
            mostrarSinPerfiles();
        }
    }
    
    /**
     * Se llama cuando no hay más perfiles que mostrar. 
     */
    private void mostrarSinPerfiles() {
        lblNombreEdad.setText("Sin perfiles nuevos");
        lblCarrera.setText("-");
        lblFotoURL.setText("-");
        txtDescripcion.setText("Vuelve a intentarlo más tarde.");
        btnDislike.setEnabled(false);
        btnLike.setEnabled(false);
    }
    
    /**
     * Muestra un error genérico.
     */
    private void mostrarError(String mensaje) {
        lblNombreEdad.setText("Error");
        txtDescripcion.setText(mensaje);
        btnDislike.setEnabled(false);
        btnLike.setEnabled(false);
    }

    /**
     * Lógica principal.
     * @param tipo El Enum TipoInteraccion (ME_GUSTA o NO_ME_INTERESA)
     */
    private void registrarInteraccion(TipoInteraccion tipo) {
        // Asegurarse que haya un perfil que reaccionar
        if (perfilActualIndex >= perfilesPorMostrar.size()) {
            return; 
        }

        Estudiante perfilMostrado = perfilesPorMostrar.get(perfilActualIndex);

        
        // 1. Crear la Clave Primaria Compuesta 
        InteraccionPK pk = new InteraccionPK(estudianteActual.getId(), perfilMostrado.getId());

        // 2. Crear la entidad Interaccion
        Interaccion interaccion = new Interaccion();
        interaccion.setId(pk); // Asignar la PK
        interaccion.setEmisor(estudianteActual); // Asignar el Emisor (mapeado a emisorId en la PK)
        interaccion.setReceptor(perfilMostrado); // Asignar el Receptor (mapeado a receptorId en la PK)
        interaccion.setTipo(tipo); // Asignar el Enum
        interaccion.setFecha(java.time.LocalDateTime.now()); // Usar setFecha como en tu entidad

        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();

            //Guardar la Interacción
            interaccionDAO.crear(interaccion, em);

            //Si fue "LIKE", verificar si hay Match
            if (tipo == TipoInteraccion.ME_GUSTA) {
                verificarMatch(em, perfilMostrado);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            mostrarError("Error al guardar la interacción.");
        } finally {
            if (em != null) em.close();
        }
        
        //Avanzar al siguiente perfil
        perfilActualIndex++;
        mostrarSiguientePerfil();
    }
    
    /**
     * Método crítico: Revisa si el "LIKE" fue mutuo y crea un Match.
     */
    private void verificarMatch(EntityManager em, Estudiante perfilMostrado) throws Exception {
        Interaccion likeMutuo = null;
        try {
            // Buscamos si ELLOS (emisor=perfilMostrado) nos dieron LIKE a NOSOTROS (receptor=estudianteActual)
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.emisor = :emisor AND i.receptor = :receptor AND i.tipo = :tipoLike", 
                Interaccion.class);
            query.setParameter("emisor", perfilMostrado);
            query.setParameter("receptor", estudianteActual);
            query.setParameter("tipoLike", TipoInteraccion.ME_GUSTA); // Usamos el Enum
            
            likeMutuo = query.getSingleResult();
            //FIN DE CONSULTA ACTUALIZADA
            
        } catch (NoResultException e) {
            likeMutuo = null; 
        }

        if (likeMutuo != null) {
            MatchConexion match = new MatchConexion();
            match.setEstudiante1(estudianteActual);
            match.setEstudiante2(perfilMostrado);
            match.setFecha(java.time.LocalDateTime.now()); 
            
            matchConexionDAO.crear(match, em);

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        this,
                        "¡Es un Match con " + perfilMostrado.getNombre() + "!",
                        "¡Match!",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        }
    }
}