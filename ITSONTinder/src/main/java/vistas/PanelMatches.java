package vistas;

import DAO.IMatchConexionDAO;
import DAO.MatchConexionDAOImpl;
import entities.Estudiante;
import entities.MatchConexion;
import persistence.JpaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import jakarta.persistence.EntityManager;

/**
 * PanelMatches
 * @author Angel Beltran
 */
public class PanelMatches extends JPanel { // Tu clase se llama PanelMatches

    // Componentes y Datos
    private JList<String> listaMatchesUI;
    private DefaultListModel<String> listModel;
    private Estudiante estudianteActual;
    private List<MatchConexion> listaDeMatches;
    private IMatchConexionDAO matchDAO;

    // Constructor
    public PanelMatches(Estudiante estudianteActual) { 
        this.estudianteActual = estudianteActual;
        this.matchDAO = new MatchConexionDAOImpl();
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblTitulo = new JLabel("Tus Matches");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        listaMatchesUI = new JList<>(listModel);
        listaMatchesUI.setFont(new Font("Arial", Font.PLAIN, 18));
        listaMatchesUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaMatchesUI);
        this.add(scrollPane, BorderLayout.CENTER);
        JLabel lblInstruccion = new JLabel("Haz doble clic en un match para chatear.", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 12));
        this.add(lblInstruccion, BorderLayout.SOUTH);

        //Eventos 
        listaMatchesUI.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    abrirChat();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("Pestaña 'Matches' visible, recargando lista...");
                cargarMatches();
            }
        });

        // Cargar Datos
        cargarMatches();
    }

    // Carga todos los matches hechos por el estudiante
    private void cargarMatches() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            listaDeMatches = matchDAO.listarMatchesPorEstudiante(estudianteActual.getId(), em);
            listModel.clear();
            if (listaDeMatches.isEmpty()) {
                listModel.addElement("Aún no tienes matches. ¡Sigue explorando!");
                listaMatchesUI.setEnabled(false);
            } else {
                listaMatchesUI.setEnabled(true);
                for (MatchConexion match : listaDeMatches) {
                    Estudiante otroEstudiante = getOtroEstudiante(match);
                    listModel.addElement(otroEstudiante.getNombre());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            listModel.clear();
            listModel.addElement("Error al cargar matches.");
        } finally {
            if (em != null) em.close();
        }
    }

    // Logica para abrir el chat
    private void abrirChat() {
        int indexSeleccionado = listaMatchesUI.getSelectedIndex();
        
        // Se selecciona
        if (indexSeleccionado == -1 || listaDeMatches.isEmpty()) {
            return;
        }

        // Se obtiene el match seleccionado
        MatchConexion matchSeleccionado = listaDeMatches.get(indexSeleccionado);
        
        // Se obtiene la ventana padre
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);

        // Se crea y se muestra el JDialog del chat
        FrameChat chatDialog = new FrameChat(owner, estudianteActual, matchSeleccionado);
        chatDialog.setVisible(true); 
    }
    
    // Para obtener al otro estudiante
    private Estudiante getOtroEstudiante(MatchConexion match) {
        if (match.getEstudiante1().getId().equals(estudianteActual.getId())) {
            return match.getEstudiante2();
        } else {
            return match.getEstudiante1();
        }
    }
}
