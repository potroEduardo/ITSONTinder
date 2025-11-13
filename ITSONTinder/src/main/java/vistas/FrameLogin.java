package vistas;

import DAO.EstudianteDAOImpl;
import DAO.IEstudianteDAO;
import entities.Estudiante; 
import persistence.JpaUtil; 
import validaciones.Validaciones;
import jakarta.persistence.EntityManager; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * FrmLoginSimple
 * @author Angel Beltran
 */
public class FrameLogin extends JFrame {

    //Componentes 
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    
    //Constructor
    public FrameLogin() {

        setTitle("ITSONTinder - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Título (NORTE)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblTitulo = new JLabel("ITSONTinder", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        //Formulario (CENTRO)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        JLabel lblCorreo = new JLabel("Correo Institucional:");
        txtCorreo = new JTextField(20);
        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField(20);
        panelFormulario.add(lblCorreo);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(lblPassword);
        panelFormulario.add(Box.createVerticalStrut(5));
        panelFormulario.add(txtPassword);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        //Botones (SUR)
        JPanel panelBotones = new JPanel(); // FlowLayout por defecto
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnRegistrarse = new JButton("Registrarse");
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistrarse);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        //Eventos
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());

        //Cierre de JPA
        // Nos aseguramos de cerrar el EntityManagerFactory al cerrar la app
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JpaUtil.getInstance().close();
                System.out.println("Cerrando aplicación y liberando recursos JPA.");
            }
        });

        //Finalizar
        add(panelPrincipal);
        pack(); // Ajusta el tamaño al contenido
    }

    //Lógica de Eventos

    //Iniciar sesion con los campos de texto recibidos
    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String password = new String(txtPassword.getPassword());

        if (Validaciones.esCampoVacio(correo) || Validaciones.esCampoVacio(password)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingresa correo y contraseña.",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.esCorreoItson(correo)) {
            JOptionPane.showMessageDialog(this,
                    "El correo debe ser de ITSON (terminar en @itson.edu.mx o @itson.mx).",
                    "Correo Inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Lógica de Negocio
        EntityManager em = null; // Se declara el EntityManager
        try {
            //Se obtiene un EntityManager de JpaUtil
            em = JpaUtil.getInstance().getEntityManager();
            
            //Se crea una instancia del DAO
            IEstudianteDAO dao = new EstudianteDAOImpl();

            //Se llama al método correcto de nuestra DAO
            Estudiante estudiante = dao.buscarPorCorreoYContrasena(correo, password, em);

            //Se verifica el resultado el resultado
            if (estudiante != null) {
                //Éxito
                JOptionPane.showMessageDialog(this,
                        "¡Bienvenido, " + estudiante.getNombre() + "!",
                        "Inicio de Sesión Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                // Se abre la ventana de inicio y se pasa el estudiante
                FrameInicio frameInicio = new FrameInicio(estudiante);
                frameInicio.setVisible(true);

                // Se cierra la ventana de login
                this.dispose();

            } else {
                // Escenario donde la contraseña o el correo son incorrectos
                JOptionPane.showMessageDialog(this,
                        "Correo o contraseña incorrectos.",
                        "Error de Autenticación",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            // Error general
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // 5. Se cierrra el entityManager
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("EntityManager cerrado (Login).");
            }
        }
    }

    // Se cierra la ventana y se abre la de registro
    private void abrirRegistro() {
        FrameRegistroEstudiante frmRegistro = new FrameRegistroEstudiante();
        frmRegistro.setVisible(true);
        this.dispose();
    }

    //Método Main
    public static void main(String[] args) {
        // Aseguramos que se cree en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new FrameLogin().setVisible(true);
        });
    }
}
