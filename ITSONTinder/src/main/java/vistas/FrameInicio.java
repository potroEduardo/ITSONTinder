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

    // --- 1. Datos ---
    private Estudiante estudianteActual;

    // --- 2. Constructor ---
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

    // --- 5. Método main (Para pruebas) ---
    /**
     * main para probar FrmInicio directamente sin pasar por el login.
     * Crea un estudiante "mock" (falso) para las pruebas.
     */
    public static void main(String[] args) {
        // Iniciar en el hilo de Swing
        EventQueue.invokeLater(() -> {
            
            // --- CREACIÓN DE ESTUDIANTE DE PRUEBA ---
            // ¡IMPORTANTE! Cambia este ID por un ID que SÍ exista en tu BD
            // (por ejemplo, el ID del usuario que registraste con éxito).
            Estudiante mockEstudiante = new Estudiante();
            mockEstudiante.setId(1); // <-- ¡CAMBIA ESTO!
            mockEstudiante.setNombre("Usuario de Prueba");
            
            // --- FIN DE MOCK ---

            try {
                // Iniciar JpaUtil (esto es crucial para el 'main' de prueba)
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