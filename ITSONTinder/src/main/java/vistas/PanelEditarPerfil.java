package vistas;

import DAO.*;
import entities.Estudiante;
import entities.Hobby;
import entities.Interes;
import jakarta.persistence.EntityManager;
import persistence.JpaUtil;
import validaciones.Validaciones;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PanelEditarPerfil 
 *@author Angel Beltran
 */
public class PanelEditarPerfil extends JPanel {

    //Componentes
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtCarrera;
    private JTextField txtFotoUrl;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbSexo;
    private JButton btnGuardar;
    private JPanel panelHobbies; //Checkboxes
    private JPanel panelIntereses;

    // Datos y DAOs
    private Estudiante estudianteActual; // El estudiante que se pasó al FrmInicio
    
    // Usaremos un Estudiante "gestionado" por JPA para evitar LazyInitializationException
    private Estudiante estudianteGestionado; 
    
    private IEstudianteDAO estudianteDAO;
    private IHobbyDAO hobbyDAO;
    private IInteresDAO interesDAO;
    private Map<Hobby, JCheckBox> mapHobbies = new HashMap<>();
    private Map<Interes, JCheckBox> mapIntereses = new HashMap<>();

    /**
     * Constructor del panel de edición de perfil.
     * @param estudianteActual El estudiante que ha iniciado sesión.
     */
    public PanelEditarPerfil(Estudiante estudianteActual) {
        this.estudianteActual = estudianteActual;
        this.estudianteDAO = new EstudianteDAOImpl();
        this.hobbyDAO = new HobbyDAOImpl();
        this.interesDAO = new InteresDAOImpl();

        // Configurar el layout principal de este panel
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Construir UI
        
        // Título (NORTE)
        JLabel lblTitulo = new JLabel("Editar Mi Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblTitulo, BorderLayout.NORTH);

        // Panel Central (Formulario)
        JPanel panelFormulario = crearPanelFormulario();
        
        // Envolvemos todo el formulario en un JScrollPane
        JScrollPane scrollFormulario = new JScrollPane(panelFormulario);
        scrollFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollFormulario.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(scrollFormulario, BorderLayout.CENTER);

        // Botón de Guardar (SUR)
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.addActionListener(e -> guardarCambios());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnGuardar);
        this.add(panelBoton, BorderLayout.SOUTH);

        // Cargar Datos
        // Usamos SwingWorker para cargar datos de BD en segundo plano
        // y no congelar la UI.
        cargarDatosAsync();
    }
    
    /**
     * Crea el panel principal del formulario con GridBagLayout.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Fila 0: Nombre
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        fila++;
        
        // Fila 1: Edad
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        txtEdad = new JTextField(20);
        panel.add(txtEdad, gbc);
        fila++;
        
        // Fila 2: Sexo
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        String[] sexos = {"Masculino", "Femenino", "Otro"};
        cmbSexo = new JComboBox<>(sexos);
        panel.add(cmbSexo, gbc);
        fila++;

        // Fila 3: Carrera
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Carrera:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        txtCarrera = new JTextField(20);
        panel.add(txtCarrera, gbc);
        fila++;
        
        // Fila 4: URL Foto
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("URL Foto Perfil:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        txtFotoUrl = new JTextField(20);
        panel.add(txtFotoUrl, gbc);
        fila++;
        
        // Fila 5: Descripción
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(new JLabel("Descripción (Bio):"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panel.add(scrollDescripcion, gbc);
        fila++;
        
        // Fila 6: Hobbies
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(new JLabel("Mis Hobbies:"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        panelHobbies = new JPanel(); // Panel para checkboxes de Hobbies
        panelHobbies.setLayout(new BoxLayout(panelHobbies, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(panelHobbies), gbc);
        fila++;
        
        // Fila 7: Intereses
        gbc.gridx = 0; gbc.gridy = fila; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(new JLabel("Busco (Intereses):"), gbc);
        gbc.gridx = 1; gbc.gridy = fila; gbc.anchor = GridBagConstraints.WEST;
        panelIntereses = new JPanel(); // Panel para checkboxes de Intereses
        panelIntereses.setLayout(new BoxLayout(panelIntereses, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(panelIntereses), gbc);
        fila++;

        return panel;
    }

    /**
     * Carga los datos del estudiante y las listas de hobbies/intereses
     * desde la BD en un hilo separado.
     */
    private void cargarDatosAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private List<Hobby> todosLosHobbies;
            private List<Interes> todosLosIntereses;
            private Set<Hobby> misHobbies;
            private Set<Interes> misIntereses;
            
            @Override
            protected Void doInBackground() throws Exception {
                EntityManager em = null;
                try {
                    em = JpaUtil.getInstance().getEntityManager();
                    
                    // Se recarga el estudiante en este EntityManager
                    estudianteGestionado = em.find(Estudiante.class, estudianteActual.getId());

                    // Se carga las listas completas
                    todosLosHobbies = hobbyDAO.listar(0, em);
                    todosLosIntereses = interesDAO.listar(0, em);
                    
                    // Se obtienen los sets del estudiante gestionado
                    misHobbies = estudianteGestionado.getHobbies();
                    misIntereses = estudianteGestionado.getIntereses();
                    misHobbies.size();
                    misIntereses.size();

                } finally {
                    if (em != null) em.close();
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); 

                    //Actualizamos la UI en el hilo de Swing
                    
                    // Campos de texto
                    txtNombre.setText(estudianteGestionado.getNombre());
                    txtEdad.setText(String.valueOf(estudianteGestionado.getEdad()));
                    cmbSexo.setSelectedItem(estudianteGestionado.getSexo());
                    txtCarrera.setText(estudianteGestionado.getCarrera());
                    txtFotoUrl.setText(estudianteGestionado.getFotoPerfilURL());
                    txtDescripcion.setText(estudianteGestionado.getDescripcion());
                    
                    // Checkboxes de Hobbies
                    mapHobbies.clear();
                    panelHobbies.removeAll();
                    for (Hobby hobby : todosLosHobbies) {
                        JCheckBox cb = new JCheckBox(hobby.getNombre());
                        // Se preeselecciona
                        if (misHobbies.contains(hobby)) {
                            cb.setSelected(true);
                        }
                        mapHobbies.put(hobby, cb);
                        panelHobbies.add(cb);
                    }
                    
                    // Checkboxes de Intereses
                    mapIntereses.clear();
                    panelIntereses.removeAll();
                    for (Interes interes : todosLosIntereses) {
                        JCheckBox cb = new JCheckBox(interes.getNombre());
                        // Se preeselecciona
                        if (misIntereses.contains(interes)) {
                            cb.setSelected(true);
                        }
                        mapIntereses.put(interes, cb);
                        panelIntereses.add(cb);
                    }
                    
                    panelHobbies.revalidate();
                    panelHobbies.repaint();
                    panelIntereses.revalidate();
                    panelIntereses.repaint();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(PanelEditarPerfil.this,
                            "Error al cargar datos del perfil: " + e.getMessage(),
                            "Error de Carga", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Valida los campos del formulario antes de guardar.
     */
    private boolean validarCampos() {
        if (Validaciones.esCampoVacio(txtNombre.getText()) || 
            Validaciones.esCampoVacio(txtEdad.getText()) || 
            Validaciones.esCampoVacio(txtCarrera.getText()) ||
            Validaciones.esCampoVacio(txtFotoUrl.getText()) ||
            Validaciones.esCampoVacio(txtDescripcion.getText())) {
            mostrarError("Todos los campos de texto son obligatorios.");
            return false;
        }
        
        if (!Validaciones.esEnteroPositivo(txtEdad.getText()) || Integer.parseInt(txtEdad.getText()) < 18) {
            mostrarError("La edad debe ser un número válido (18+).");
            return false;
        }
        
        if (!Validaciones.esSoloTexto(txtNombre.getText())) {
            mostrarError("El nombre solo debe contener letras.");
            return false;
        }
        // (Podríamos añadir más validaciones)
        return true;
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Guarda los cambios en la base de datos.
     */
    private void guardarCambios() {
        if (!validarCampos()) {
            return;
        }

        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();

            // Se vuelve a adjuntar (merge) nuestro estudiante al contexto
            Estudiante estudianteParaGuardar = em.merge(estudianteGestionado);
            
            // Se actualizan los campos de texto
            estudianteParaGuardar.setNombre(txtNombre.getText());
            estudianteParaGuardar.setEdad(Integer.parseInt(txtEdad.getText()));
            estudianteParaGuardar.setSexo(cmbSexo.getSelectedItem().toString());
            estudianteParaGuardar.setCarrera(txtCarrera.getText());
            estudianteParaGuardar.setFotoPerfilURL(txtFotoUrl.getText());
            estudianteParaGuardar.setDescripcion(txtDescripcion.getText());

            // Se actualizan los hobbies
            estudianteParaGuardar.getHobbies().clear(); // Borramos los hobbies actuales
            for (Map.Entry<Hobby, JCheckBox> entry : mapHobbies.entrySet()) {
                if (entry.getValue().isSelected()) {
                    estudianteParaGuardar.getHobbies().add(entry.getKey());
                }
            }
            
            // Se actualizan intereses
            estudianteParaGuardar.getIntereses().clear(); // Borramos los intereses actuales
            for (Map.Entry<Interes, JCheckBox> entry : mapIntereses.entrySet()) {
                if (entry.getValue().isSelected()) {
                    estudianteParaGuardar.getIntereses().add(entry.getKey());
                }
            }

            // Se llama a la estudianteDAO Actualizar
            estudianteDAO.actualizar(estudianteParaGuardar, em);
            
            // Se confirma la transaccion
            em.getTransaction().commit();
            
            // Se actualiza el estudiante gestionado
            this.estudianteGestionado = estudianteParaGuardar;

            JOptionPane.showMessageDialog(this,
                    "¡Perfil actualizado con éxito!",
                    "Guardado",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this,
                    "Error al guardar los cambios: " + e.getMessage(),
                    "Error de Guardado",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) em.close();
        }
    }
}
