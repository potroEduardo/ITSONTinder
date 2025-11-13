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
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection; 
import java.util.List;

/**
 * PnlExplorar 
 * @author Angel Beltran
 */
public class PanelExplorar extends JPanel { 

    private JLabel lblNombreEdad;
    private JLabel lblCarrera;
    private JLabel lblFotoContenedor;
    private JTextArea txtDescripcion;
    private JButton btnDislike;
    private JButton btnLike;
    private JPanel panelPerfil;
    private JPanel panelBotones;

    // Datos y Lógica
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

        // Construir UI
        
        // Panel Central (Información del Perfil)
        panelPerfil = new JPanel(new BorderLayout(10, 10));

        // Contenedor de la Foto (CENTRO)
        lblFotoContenedor = new JLabel("Cargando foto...", SwingConstants.CENTER);
        lblFotoContenedor.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFotoContenedor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        lblFotoContenedor.setOpaque(true);
        lblFotoContenedor.setBackground(Color.DARK_GRAY);
        lblFotoContenedor.setPreferredSize(new Dimension(400, 400));
        panelPerfil.add(lblFotoContenedor, BorderLayout.CENTER);

        // Panel de Texto (SUR)
        JPanel panelInfoTexto = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        
        lblNombreEdad = new JLabel("Cargando...", SwingConstants.CENTER);
        lblNombreEdad.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        panelInfoTexto.add(lblNombreEdad, gbc);

        lblCarrera = new JLabel("...", SwingConstants.CENTER);
        lblCarrera.setFont(new Font("Arial", Font.ITALIC, 16));
        gbc.gridy = 1;
        panelInfoTexto.add(lblCarrera, gbc);
        
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panelInfoTexto.add(scrollDescripcion, gbc);
        
        panelPerfil.add(panelInfoTexto, BorderLayout.SOUTH);
        
        this.add(panelPerfil, BorderLayout.CENTER);

        //Panel Sur (Botones)
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnDislike = new JButton("Dislike"); // Usando el texto de tu archivo
        btnLike = new JButton("Like"); // Usando el texto de tu archivo
        panelBotones.add(btnDislike);
        panelBotones.add(btnLike);
        
        this.add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnDislike.addActionListener(e -> registrarInteraccion(TipoInteraccion.NO_ME_INTERESA));
        btnLike.addActionListener(e -> registrarInteraccion(TipoInteraccion.ME_GUSTA));

        // Cargar Datos Iniciales
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
     * Muestra el siguiente perfil en la UI.
     */
    private void mostrarSiguientePerfil() {
        if (perfilActualIndex < perfilesPorMostrar.size()) {
            Estudiante perfil = perfilesPorMostrar.get(perfilActualIndex);
            
            lblNombreEdad.setText(perfil.getNombre() + ", " + perfil.getEdad());
            lblCarrera.setText(perfil.getCarrera());
            txtDescripcion.setText(perfil.getDescripcion());

            // Llamamos al método corregido para cargar la imagen
            cargarImagenAsync(perfil.getFotoPerfilURL(), lblFotoContenedor);

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
        txtDescripcion.setText("No hay más perfiles por ahora. Vuelve a intentarlo más tarde.");
        lblFotoContenedor.setIcon(null);
        lblFotoContenedor.setText(":/");
        
        btnDislike.setEnabled(false);
        btnLike.setEnabled(false);
    }
    

    /**
     * Muestra un mensaje de error en el panel.
     */
    private void mostrarError(String mensaje) {
        lblNombreEdad.setText("Error");
        txtDescripcion.setText(mensaje);
        lblFotoContenedor.setIcon(null);
        lblFotoContenedor.setText("Error");
        
        btnDislike.setEnabled(false);
        btnLike.setEnabled(false);
    }

    /**
     * Lógica principal. Registra un "LIKE" o "DISLIKE".
     */
    private void registrarInteraccion(TipoInteraccion tipo) {
        if (perfilActualIndex >= perfilesPorMostrar.size()) {
            return; 
        }
        Estudiante perfilMostrado = perfilesPorMostrar.get(perfilActualIndex);
        InteraccionPK pk = new InteraccionPK(estudianteActual.getId(), perfilMostrado.getId());
        Interaccion interaccion = new Interaccion();
        interaccion.setId(pk);
        interaccion.setEmisor(estudianteActual);
        interaccion.setReceptor(perfilMostrado);
        interaccion.setTipo(tipo);
        interaccion.setFecha(java.time.LocalDateTime.now());
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            interaccionDAO.crear(interaccion, em);
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
        perfilActualIndex++;
        mostrarSiguientePerfil();
    }
    
    /**
     * Método crítico: Revisa si el "LIKE" fue mutuo y crea un Match.
     */
    private void verificarMatch(EntityManager em, Estudiante perfilMostrado) throws Exception {
        Interaccion likeMutuo = null;
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.emisor = :emisor AND i.receptor = :receptor AND i.tipo = :tipoLike", 
                Interaccion.class);
            query.setParameter("emisor", perfilMostrado);
            query.setParameter("receptor", estudianteActual);
            query.setParameter("tipoLike", TipoInteraccion.ME_GUSTA);
            likeMutuo = query.getSingleResult();
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
    
    
    /**
     * Carga una imagen desde una URL en un hilo de fondo (SwingWorker).
     * @param urlImagen La URL (String) de la imagen a descargar.
     * @param labelContenedor El JLabel donde se mostrará la imagen.
     */
    private void cargarImagenAsync(String urlImagen, JLabel labelContenedor) {
        
        // Ponemos el "Cargando"
        labelContenedor.setIcon(null);
        labelContenedor.setText("Cargando foto...");

        // Validación INMEDIATA (antes de crear el hilo)
        if (urlImagen == null || urlImagen.trim().isEmpty()) {
            labelContenedor.setText("Foto no disponible");
            return; // No continuamos si no hay URL
        }

        // SwingWorker para descargar la imagen en segundo plano
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
            
            @Override
            protected ImageIcon doInBackground() throws Exception {
                // Esto se ejecuta en un hilo separado
                try {
                    URL url = new URL(urlImagen);
                    
                    // --- INICIO DE LA MODIFICACIÓN (PARA EVITAR HTTP 403) ---
                    // Nos conectamos manualmente y fingimos ser un navegador
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
                    
                    // Leemos la imagen desde la conexión, no desde la URL
                    BufferedImage imagenOriginal = ImageIO.read(connection.getInputStream());
                    // --- FIN DE LA MODIFICACIÓN ---
                    
                    if (imagenOriginal == null) {
                        throw new Exception("No se pudo leer la imagen");
                    }

                    // Escalar la imagen para que quepa en el contenedor
                    // (Manteniendo la proporción)
                    int anchoContenedor = 400; // Ancho preferido
                    int altoContenedor = 400; // Alto preferido
                    
                    int anchoImg = imagenOriginal.getWidth();
                    int altoImg = imagenOriginal.getHeight();

                    float ratioImg = (float) anchoImg / (float) altoImg;
                    float ratioCont = (float) anchoContenedor / (float) altoContenedor;

                    int nuevoAncho;
                    int nuevoAlto;

                    // Ajustar al contenedor (como 'cover' en CSS)
                    if (ratioCont > ratioImg) {
                        nuevoAncho = anchoContenedor;
                        nuevoAlto = (int) (nuevoAncho / ratioImg);
                    } else {
                        nuevoAlto = altoContenedor;
                        nuevoAncho = (int) (nuevoAlto * ratioImg);
                    }
                    
                    Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                    return new ImageIcon(imagenEscalada);

                } catch (Exception e) {
                    e.printStackTrace(); // Ver el error en consola
                    return null; // Indicar que falló
                }
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icono = get(); 
                    if (icono != null) {
                        labelContenedor.setIcon(icono);
                        labelContenedor.setText(null); 
                    } else {
                        throw new Exception("Icono nulo"); 
                    }
                } catch (Exception e) {
                    // Si falló la descarga
                    labelContenedor.setIcon(null);
                    labelContenedor.setText("Foto no disponible");
                }
            }
        };
        worker.execute(); 
    }
    
}