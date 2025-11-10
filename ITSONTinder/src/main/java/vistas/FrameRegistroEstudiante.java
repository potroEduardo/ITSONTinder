package vistas;

import DAO.EstudianteDAOImpl;
import DAO.IEstudianteDAO;
import entities.Estudiante; 
import persistence.JpaUtil; 
import validaciones.Validaciones;
import jakarta.persistence.EntityManager; 
import jakarta.persistence.RollbackException; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * FrmRegistroEstudiante 
 * @author Angel Beltran
 */
public class FrameRegistroEstudiante extends JFrame {

    //Componentes
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmarPassword;
    private JTextField txtEdad;
    private JComboBox<String> cmbSexo;
    private JTextField txtCarrera;
    private JTextField txtFotoUrl;
    private JTextArea txtDescripcion;
    private JButton btnRegistrarse;
    private JButton btnVolver;

    //Constructor
    public FrameRegistroEstudiante() {

        //Configuración de la Ventana
        setTitle("ITSONTinder - Registro");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Panel Principal (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Título (NORTE)
        JLabel lblTitulo = new JLabel("Crear Nueva Cuenta", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        //b) Formulario (CENTRO)
        // Usamos GridBagLayout para alinear etiquetas y campos
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellenar horizontalmente
        
        // Fila 0: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Fila 1: Correo
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Correo ITSON:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo, gbc);

        // Fila 2: Contraseña
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword, gbc);

        // Fila 3: Confirmar Contraseña
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        txtConfirmarPassword = new JPasswordField();
        panelFormulario.add(txtConfirmarPassword, gbc);

        // Fila 4: Edad
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad, gbc);

        // Fila 5: Sexo
        gbc.gridx = 0; gbc.gridy = 5;
        panelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        String[] sexos = {"Masculino", "Femenino", "Otro"};
        cmbSexo = new JComboBox<>(sexos);
        panelFormulario.add(cmbSexo, gbc);

        // Fila 6: Carrera
        gbc.gridx = 0; gbc.gridy = 6;
        panelFormulario.add(new JLabel("Carrera:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        txtCarrera = new JTextField();
        panelFormulario.add(txtCarrera, gbc);

        // Fila 7: Descripción (Bio)
        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.NORTH;
        panelFormulario.add(new JLabel("Descripción (Bio):"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelFormulario.add(scrollDescripcion, gbc);
        
        // Fila 8: URL de Foto
        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(new JLabel("URL de Foto de Perfil:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; gbc.anchor = GridBagConstraints.WEST;
        txtFotoUrl = new JTextField(20);
        panelFormulario.add(txtFotoUrl, gbc);

        panelPrincipal.add(new JScrollPane(panelFormulario), BorderLayout.CENTER);

        //Botones (SUR)
        JPanel panelBotones = new JPanel(); // FlowLayout por defecto
        btnRegistrarse = new JButton("Registrarse");
        btnVolver = new JButton("Volver al Login");
        panelBotones.add(btnRegistrarse);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        //3. Eventos
        btnRegistrarse.addActionListener(e -> registrarEstudiante());
        btnVolver.addActionListener(e -> abrirLogin());
        
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
    //Validad los campos de el Formulario
    private boolean validarCampos() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String password = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmarPassword.getPassword());
        String edad = txtEdad.getText();
        String carrera = txtCarrera.getText();
        String descripcion = txtDescripcion.getText();

        if (Validaciones.esCampoVacio(nombre) || Validaciones.esCampoVacio(correo) ||
            Validaciones.esCampoVacio(password) || Validaciones.esCampoVacio(confirmar) ||
            Validaciones.esCampoVacio(edad) || Validaciones.esCampoVacio(carrera) ||
            Validaciones.esCampoVacio(descripcion)) {
            mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (!Validaciones.esSoloTexto(nombre)) {
            mostrarError("El nombre solo debe contener letras y espacios.");
            return false;
        }

        if (!Validaciones.esCorreoItson(correo)) {
            mostrarError("El correo debe ser de ITSON (@itson.edu.mx o @itson.mx).");
            return false;
        }

        if (!Validaciones.esPasswordValida(password, 6)) {
            mostrarError("La contraseña debe tener al menos 6 caracteres.");
            return false;
        }

        if (!Validaciones.sonCamposIguales(password, confirmar)) {
            mostrarError("Las contraseñas no coinciden.");
            return false;
        }

        if (!Validaciones.esEnteroPositivo(edad)) {
            mostrarError("La edad debe ser un número positivo (ej: 18).");
            return false;
        }
        
        if (Integer.parseInt(edad) < 18) {
            mostrarError("Debes ser mayor de edad para registrarte.");
            return false;
        }

        if (!Validaciones.esSoloTexto(carrera)) {
            mostrarError("La carrera solo debe contener letras y espacios.");
            return false;
        }

        return true;
    }

     /**
     *Registrar al estudiante en la base de datos.
     */
    private void registrarEstudiante() {
        //Validar
        if (!validarCampos()) {
            return; 
        }

        //Crear Objeto
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(txtNombre.getText());
        estudiante.setCorreoInstitucional(txtCorreo.getText()); // (Confirmado de GitHub)
        estudiante.setContrasena(new String(txtPassword.getPassword())); // (Confirmado de GitHub)
        estudiante.setEdad(Integer.parseInt(txtEdad.getText()));
        estudiante.setSexo(cmbSexo.getSelectedItem().toString());
        estudiante.setCarrera(txtCarrera.getText());
        estudiante.setFotoPerfilURL(txtFotoUrl.getText()); // ¡CAMPO NUEVO!
        estudiante.setDescripcion(txtDescripcion.getText());
        
        // La lista 'intereses' se deja vacía por ahora, se puede
        // inicializar en el constructor de la entidad o se deja null
        // y se llena después en "Editar Perfil".

        //Guardar en BD
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            IEstudianteDAO dao = new EstudianteDAOImpl();

            // Iniciamos la transacción
            em.getTransaction().begin();
            
            // Usamos el método '¿crear de nuestra DAO
            dao.crear(estudiante, em);
            
            // Si todo fue bien, confirmamos la transacción
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this,
                    "¡Registro exitoso! Ahora puedes iniciar sesión.",
                    "Registro Completo",
                    JOptionPane.INFORMATION_MESSAGE);
            abrirLogin();

        } catch (RollbackException e) {
            // Error csi el correo ya existe
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            mostrarError("No se pudo completar el registro. Es posible que el correo ya esté en uso.");
            e.printStackTrace();
        } catch (Exception e) {
            // Cualquier otro error
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            mostrarError("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos el EntityManager
            if (em != null) {
                em.close();
                System.out.println("EntityManager cerrado (Registro).");
            }
        }
    }

    /**
     * Muestra un diálogo de error. 
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Cierra esta ventana y abre el Login.
     */
    private void abrirLogin() {
        FrameLogin frmLogin = new FrameLogin(); 
        frmLogin.setVisible(true);
        this.dispose();
    }
}