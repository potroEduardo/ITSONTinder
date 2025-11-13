package vistas;

import DAO.IMensajeDAO;
import DAO.MensajeDAOImpl;
import entities.Estudiante;
import entities.MatchConexion;
import entities.Mensaje;
import jakarta.persistence.EntityManager;
import persistence.JpaUtil;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * FrameChat
 * @author Angel Beltran
 */
public class FrameChat extends JDialog {

    //Componentes
    private JTextArea txtHistorialChat;
    private JTextField txtMensajeNuevo;
    private JButton btnEnviar;

    //Datos
    private Estudiante estudianteActual;
    private MatchConexion matchSeleccionado;
    private IMensajeDAO mensajeDAO;

    /**
     * Constructor de la ventana de chat.
     * @param parent El frame padre 
     * @param estudianteActual El usuario que está logueado
     * @param matchSeleccionado El match con el que se va a chatear
     */
    public FrameChat(Frame parent, Estudiante estudianteActual, MatchConexion matchSeleccionado) {
        // Configuración del JDialog
        // Si es true bloquea la ventana principal hasta que esta se cierre
        super(parent, true); 
        
        this.estudianteActual = estudianteActual;
        this.matchSeleccionado = matchSeleccionado;
        this.mensajeDAO = new MensajeDAOImpl();
        Estudiante otroEstudiante = getOtroEstudiante(matchSeleccionado);
        
        setTitle("Chat con " + otroEstudiante.getNombre());
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        //Historial del Chat (CENTRO)
        txtHistorialChat = new JTextArea();
        txtHistorialChat.setEditable(false);
        txtHistorialChat.setLineWrap(true);
        txtHistorialChat.setWrapStyleWord(true);
        txtHistorialChat.setFont(new Font("Arial", Font.PLAIN, 14));
        //Se pone en un JScrollPane
        JScrollPane scrollChat = new JScrollPane(txtHistorialChat);
        
        add(scrollChat, BorderLayout.CENTER);

        //Panel de Envío (SUR)
        JPanel panelEnvio = new JPanel(new BorderLayout(5, 5));
        
        txtMensajeNuevo = new JTextField();
        btnEnviar = new JButton("Enviar");
        
        panelEnvio.add(txtMensajeNuevo, BorderLayout.CENTER);
        panelEnvio.add(btnEnviar, BorderLayout.EAST);
        
        // Se añade un borde para separar y que no se vea feo
        panelEnvio.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        add(panelEnvio, BorderLayout.SOUTH);

        // Eventos
        btnEnviar.addActionListener(e -> enviarMensaje());
        // Permitir enviar con la tecla "Enter"
        txtMensajeNuevo.addActionListener(e -> enviarMensaje());

        // Cargar Datos
        cargarChat();
    }

    // Se carga el historial de mensajes no supe como hacer en tiempo real
    private void cargarChat() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            
            // Se usa el metodo Dao
            List<Mensaje> mensajes = mensajeDAO.listarMensajesPorMatch(matchSeleccionado.getId(), em);
            
            // Se limpia el historial
            txtHistorialChat.setText("");
            
            // Se llena el JTextArea
            for (Mensaje msg : mensajes) {
                String nombreEmisor = msg.getEmisor().getNombre();
                
                //Se formatea para ver quien lo dijo
                if (msg.getEmisor().getId().equals(estudianteActual.getId())) {
                    txtHistorialChat.append("Tú: " + msg.getContenido() + "\n");
                } else {
                    txtHistorialChat.append(nombreEmisor + ": " + msg.getContenido() + "\n");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            txtHistorialChat.setText("Error al cargar el historial de chat.");
        } finally {
            if (em != null) em.close();
        }
    }

    // Se envia un nuevo mensaje
    private void enviarMensaje() {
        String contenido = txtMensajeNuevo.getText();
        if (contenido.trim().isEmpty()) {
            return; // No enviar mensajes vacíos
        }

        // Crear el objeto Mensaje
        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setMatch(matchSeleccionado);
        nuevoMensaje.setEmisor(estudianteActual);
        nuevoMensaje.setContenido(contenido);
        nuevoMensaje.setFecha(LocalDateTime.now()); // JPA manejará la fecha, pero es bueno tenerla

        // Guardar en la BD
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            mensajeDAO.crear(nuevoMensaje, em);
            
            em.getTransaction().commit();

            // Actualizar la UI localmente
            txtHistorialChat.append("Tú: " + contenido + "\n");
            txtMensajeNuevo.setText(""); // Se limpia el campo

        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this,
                    "Error al enviar el mensaje: " + e.getMessage(),
                    "Error de Red",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) em.close();
        }
    }

    // Metodo para obtener el otro estudiante del match
    private Estudiante getOtroEstudiante(MatchConexion match) {
        if (match.getEstudiante1().getId().equals(estudianteActual.getId())) {
            return match.getEstudiante2();
        } else {
            return match.getEstudiante1();
        }
    }
}
