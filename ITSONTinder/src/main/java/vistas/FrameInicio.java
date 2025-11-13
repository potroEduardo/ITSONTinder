package vistas;

import entities.Estudiante;
import persistence.JpaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * FrmInicio ventana principal
 * @author Angel Beltran
 */
public class FrameInicio extends JFrame {

    // Datos
    private Estudiante estudianteActual;

    // Constructor
    public FrameInicio(Estudiante estudianteActual) {
        this.estudianteActual = estudianteActual;

        // Configuración de la Ventana
        setTitle("ITSONTinder - Inicio");
        setSize(600, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Componentes 
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        // Pestaña 1: Explorar
        PanelExplorar panelExplorar = new PanelExplorar(estudianteActual);
        tabbedPane.addTab("Explorar", panelExplorar);

        // Pestaña 2: Matches
        PanelMatches panelMatches = new PanelMatches(estudianteActual);
        tabbedPane.addTab("Matches", panelMatches);
        
        // Pestaña 3: Editar Perfil
        PanelEditarPerfil panelEditarPerfil = new PanelEditarPerfil(estudianteActual);
        tabbedPane.addTab("Perfil", panelEditarPerfil);
        this.add(tabbedPane, BorderLayout.CENTER);
        
        // Pestaña 4: Administracion de estudiantes
        PanelAdminEstudiantes panelAdmin = new PanelAdminEstudiantes(estudianteActual);
        tabbedPane.addTab("Admin", panelAdmin);

        // Eventos
        // Cierre de JPA
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JpaUtil.getInstance().close();
                System.out.println("Cerrando aplicación y liberando recursos JPA.");
            }
        });
    }

    //  Método main 
    public static void main(String[] args) {
        // Iniciar en el hilo de Swing
        EventQueue.invokeLater(() -> {
            
            // Se que no se usa este mock pero tampoco lo eliminare por que el proyecto funciona bien
            Estudiante mockEstudiante = new Estudiante();
            mockEstudiante.setId(1); 
            mockEstudiante.setNombre("Usuario de Prueba");
            
           
            try {
                JpaUtil.getInstance().getEntityManager().close(); 
                System.out.println("JPA inicializado para prueba.");

                FrameInicio frame = new FrameInicio(mockEstudiante);
                frame.setVisible(true);

            } catch (Exception e) {
                System.err.println("Error al iniciar FrameInicio (main): " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}