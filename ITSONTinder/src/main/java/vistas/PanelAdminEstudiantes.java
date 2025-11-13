package vistas;

import DAO.EstudianteDAOImpl;
import DAO.IEstudianteDAO;
import entities.Estudiante;
import jakarta.persistence.EntityManager;
import persistence.JpaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * PanelAdminEstudiantes
 * @author Angel Beltran
 */
public class PanelAdminEstudiantes extends JPanel {

    // Componentes
    private JList<String> listaEstudiantesUI;
    private DefaultListModel<String> listModel;
    private JButton btnEliminar;

    // Datos 
    private Estudiante estudianteActual; // Para no eliminarse a si mismo
    private List<Estudiante> listaDeEstudiantes; // Para mapear los objetos con el java swing
    private IEstudianteDAO estudianteDAO;

    /**
     * Constructor del panel de administración de estudiantes.
     * @param estudianteActual El estudiante que ha iniciado sesión.
     */
    public PanelAdminEstudiantes(Estudiante estudianteActual) {
        this.estudianteActual = estudianteActual;
        this.estudianteDAO = new EstudianteDAOImpl();

        // Configuración del Layout
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título (NORTE)
        JLabel lblTitulo = new JLabel("Panel de Administración de Estudiantes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // Lista de Estudiantes (CENTRO)
        listModel = new DefaultListModel<>();
        listaEstudiantesUI = new JList<>(listModel);
        listaEstudiantesUI.setFont(new Font("Arial", Font.PLAIN, 16));
        listaEstudiantesUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(listaEstudiantesUI);
        add(scrollPane, BorderLayout.CENTER);

        // Botón de Eliminar (SUR)
        btnEliminar = new JButton("Eliminar Estudiante Seleccionado");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setForeground(Color.RED); // Color peligroso
        add(btnEliminar, BorderLayout.SOUTH);

        // Eventos
        
        // Evento del botón
        btnEliminar.addActionListener(e -> eliminarEstudianteSeleccionado());

        // Evento para recargar la lista cuando se muestra la pestaña
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarEstudiantes();
            }
        });

        // Carga inicial
        cargarEstudiantes();
    }

    /**
     * Carga la lista COMPLETA de estudiantes usando estudianteDAO.listar()
     */
    private void cargarEstudiantes() {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();

            listaDeEstudiantes = estudianteDAO.listar(0, em); 
            
            listModel.clear();

            if (listaDeEstudiantes.isEmpty()) {
                listModel.addElement("No hay estudiantes registrados.");
            } else {
                for (Estudiante est : listaDeEstudiantes) {
                    String info = est.getNombre() + " (ID: " + est.getId() + ", Correo: " + est.getCorreoInstitucional();
                    if (est.getId().equals(estudianteActual.getId())) {
                        info += " <-- (TÚ)";
                    }
                    listModel.addElement(info);
                }
            }
        } catch (Exception e) {
            listModel.clear();
            listModel.addElement("Error al cargar la lista de estudiantes.");
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    // Para eliminar al estudiante que es seleccionado
    private void eliminarEstudianteSeleccionado() {
        int indexSeleccionado = listaEstudiantesUI.getSelectedIndex();

        if (indexSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un estudiante de la lista.", "Nada seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtenemos el estudiante seleccionado
        Estudiante estudianteAEliminar = listaDeEstudiantes.get(indexSeleccionado);

        // Verificacion para que el estudiante no se elimine asi mismo
        if (estudianteAEliminar.getId().equals(estudianteActual.getId())) {
            JOptionPane.showMessageDialog(this, "No puedes eliminarte a ti mismo desde esta pantalla.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Dialog de confirmacion
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Estás seguro de que quieres eliminar a " + estudianteAEliminar.getNombre() + "?\n" +
                "esto borrara todo de el",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            //Se usa el metodo crud aqui
            estudianteDAO.eliminar(estudianteAEliminar, em); 
            
            em.getTransaction().commit();
            
            JOptionPane.showMessageDialog(this, "Estudiante " + estudianteAEliminar.getNombre() + " eliminado con éxito.", "Eliminación Completa", JOptionPane.INFORMATION_MESSAGE);
            
            // Recargamos la lista para que desaparezca
            cargarEstudiantes(); 

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Error al eliminar al estudiante: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}
