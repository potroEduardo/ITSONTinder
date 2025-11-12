package vistas;

import DAO.IMatchConexionDAO;
import DAO.MatchConexionDAOImpl;
import entities.Estudiante;
import entities.MatchConexion;
import persistence.JpaUtil;

import javax.swing.*;
import java.awt.*;
// --- IMPORTS AÑADIDOS ---
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
// --- FIN IMPORTS AÑADIDOS ---
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import jakarta.persistence.EntityManager;

/**
 * PanelMatches
 * @author Angel Beltran
 */
public class PanelMatches extends JPanel { 

    // Componentes
    private JList<String> listaMatchesUI;
    private DefaultListModel<String> listModel;

    // Datos
    private Estudiante estudianteActual;
    private List<MatchConexion> listaDeMatches;
    private IMatchConexionDAO matchDAO;

    /**
     * Constructor del panel de matches.
     * @param estudianteActual El estudiante que ha iniciado sesión.
     */
    public PanelMatches(Estudiante estudianteActual) { // Tu constructor
        this.estudianteActual = estudianteActual;
        this.matchDAO = new MatchConexionDAOImpl();

        // Layout Configuracion
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Construir UI
        
        //Título (NORTE)
        JLabel lblTitulo = new JLabel("Tus Matches");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        //Lista (CENTRO)
        listModel = new DefaultListModel<>();
        listaMatchesUI = new JList<>(listModel);
        listaMatchesUI.setFont(new Font("Arial", Font.PLAIN, 18));
        listaMatchesUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Se envuelve la lista en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(listaMatchesUI);
        this.add(scrollPane, BorderLayout.CENTER);

        //Instrucción (SUR)
        JLabel lblInstruccion = new JLabel("Haz doble clic en un match para chatear.", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Arial", Font.ITALIC, 12));
        this.add(lblInstruccion, BorderLayout.SOUTH);

        //Eventos
        listaMatchesUI.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // Doble clic detectado
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
        // FIN DE LA CORRECCIÓN

        // Cargar Datos 
        cargarMatches();
    }

    // Se cargan desde la BD y se crea la JList
    private void cargarMatches() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            
            // Se usa Listar MatchesPorEstudiante
            listaDeMatches = matchDAO.listarMatchesPorEstudiante(estudianteActual.getId(), em);

            // Se limpia la lista gráfica
            listModel.clear();
            
            if (listaDeMatches.isEmpty()) {
                listModel.addElement("Aún no tienes matches. ¡Sigue explorando!");
                listaMatchesUI.setEnabled(false);
            } else {
                listaMatchesUI.setEnabled(true); // Asegurarse de que esté habilitada
                // Se llena la lista grafica
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

    private void abrirChat() {
        int indexSeleccionado = listaMatchesUI.getSelectedIndex();

        if (indexSeleccionado == -1 || listaDeMatches.isEmpty()) {
            return;
        }

        MatchConexion matchSeleccionado = listaDeMatches.get(indexSeleccionado);
        

        JOptionPane.showMessageDialog(this, 
                "Iniciando chat con: " + getOtroEstudiante(matchSeleccionado).getNombre(),
                "Próximamente",
                JOptionPane.INFORMATION_MESSAGE);
        
    }

    private Estudiante getOtroEstudiante(MatchConexion match) {
        if (match.getEstudiante1().getId().equals(estudianteActual.getId())) {
            return match.getEstudiante2();
        } else {
            return match.getEstudiante1();
        }
    }
}
