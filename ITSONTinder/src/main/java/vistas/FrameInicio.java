package vistas;

import entities.Estudiante; 
import persistence.JpaUtil; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 

/**
 * FrmInicio 
 * @author Angel Beltran
 */
public class FrameInicio extends JFrame {

    //Componentes
    private Estudiante estudianteActual;
    private JLabel lblBienvenida;
    private JButton btnCerrarSesion;
    private JTabbedPane tabbedPane; // Jpanel de Exploracion

    //Constructor
    public FrameInicio(Estudiante estudiante) {
        this.estudianteActual = estudiante;

        //Configuración de la Ventana 
        setTitle("ITSONTinder - Inicio");
        setSize(500, 700); // Tamaño ajustado para el panel de swipe
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel Principal (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Cabecera (NORTE) (Sin cambios)
        JPanel panelCabecera = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Alineado a la derecha
        
        lblBienvenida = new JLabel("¡Bienvenido, " + estudianteActual.getNombre() + "!");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        
        btnCerrarSesion = new JButton("Cerrar Sesión");
        
        panelCabecera.add(lblBienvenida);
        panelCabecera.add(btnCerrarSesion);
        
        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        //Área Principal (CENTRO)
        
        // 1. Crear el JTabbedPane
        tabbedPane = new JTabbedPane();

        // 2. Crear el Panel de Explorar (nuestro nuevo panel)
        PanelExplorar panelExplorar = new PanelExplorar(estudianteActual);
        
        // 3. Crear un Panel placeholder para Matches
        JPanel panelMatches = new JPanel();
        panelMatches.add(new JLabel("Aquí se mostrará tu lista de Matches. (Próximamente)"));

        // 4. Añadir las pestañas
        tabbedPane.addTab("Explorar", panelExplorar);
        tabbedPane.addTab("Matches", panelMatches);
        
        // 5. Añadir el JTabbedPane al centro del frame
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        

        //3. Eventos
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        //Cierre de JPA
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JpaUtil.getInstance().close();
                System.out.println("Cerrando aplicación y liberando recursos JPA.");
            }
        });

        //Finalizar
        add(panelPrincipal);
    }

    //Métodos de Lógica
    
    private void cerrarSesion() {
        // Mostramos confirmación
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres cerrar sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            // Cerramos esta ventana
            this.dispose();
            // Abrimos el Login
            FrameLogin frameLogin = new FrameLogin();
            frameLogin.setVisible(true);
        }
    }
    
    
    // MÉTODO MAIN 
    /**
     * Método main para probar este JFrame individualmente.
     * Crea un estudiante "mock" (falso) para propósitos de prueba,
     * ya que FrmInicio necesita un Estudiante en su constructor.
     */
    public static void main(String[] args) {
        
        //Crear un Estudiante mock 
        Estudiante estudianteDePrueba = new Estudiante();
        estudianteDePrueba.setId(999); // Un ID de prueba
        estudianteDePrueba.setNombre("Usuario de Prueba");
        estudianteDePrueba.setEdad(21);
        estudianteDePrueba.setCarrera("ISC (Prueba)");
        estudianteDePrueba.setDescripcion("Esta es una descripción de prueba para el panel.");
        estudianteDePrueba.setFotoPerfilURL("http://via.placeholder.com/300");
        
        
        //Iniciar la UI
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //Se crea el FrmInicio pasándole el estudiante mock
                    FrameInicio frame = new FrameInicio(estudianteDePrueba);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}