package interfasUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfas.GestorUsuario;
import main.Consola;
import users.Usuario;
import users.Cliente; 

public class VentanaPrincipal extends JFrame {
    
    private JTextField txtLoginUsuario;
    private JPasswordField txtLoginPassword;
    private JButton btnIngresar;
    
    private JTextField txtRegNombre;
    private JTextField txtRegEmail;
    private JTextField txtRegLogin;
    private JPasswordField txtRegPassword;
    private JTextField txtRegFechaNac;
    private JButton btnRegistrar;
    
    private GestorUsuario gestorU;
    private Consola consolaBase;

    public VentanaPrincipal(GestorUsuario gestorU, Consola consolaBase) {
        this.gestorU = gestorU;
        this.consolaBase = consolaBase;
        
        setTitle("Board Game Café - Bienvenido");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JTabbedPane pestañas = new JTabbedPane();
        
        pestañas.addTab("Iniciar Sesión", crearPanelLogin());
        pestañas.addTab("Registrarse como Cliente", crearPanelRegistro());
        
        add(pestañas);
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario (Login):"), gbc);
        txtLoginUsuario = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtLoginUsuario, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtLoginPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtLoginPassword, gbc);

        // Botón Ingresar
        btnIngresar = new JButton("Ingresar al Sistema");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        // Evento de Login
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarLogin();
            }
        });

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre Completo:"), gbc);
        txtRegNombre = new JTextField(15);
        gbc.gridx = 1; panel.add(txtRegNombre, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        txtRegEmail = new JTextField(15);
        gbc.gridx = 1; panel.add(txtRegEmail, gbc);

        // Login 
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Nombre de Usuario (Login):"), gbc);
        txtRegLogin = new JTextField(15);
        gbc.gridx = 1; panel.add(txtRegLogin, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Contraseña:"), gbc);
        txtRegPassword = new JPasswordField(15);
        gbc.gridx = 1; panel.add(txtRegPassword, gbc);

        // Fecha de Nacimiento
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("F. Nacimiento (AAAA-MM-DD):"), gbc);
        txtRegFechaNac = new JTextField(15);
        gbc.gridx = 1; panel.add(txtRegFechaNac, gbc);

        // Botón Registrar
        btnRegistrar = new JButton("Crear Cuenta de Cliente");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        // Evento de Registro
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRegistro();
            }
        });

        return panel;
    }

    private void procesarLogin() {
        String loginInput = txtLoginUsuario.getText().trim();
        String passwordInput = new String(txtLoginPassword.getPassword()).trim();

        if (loginInput.isEmpty() || passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos incompletos en el Login.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = gestorU.buscarUsuario(loginInput);

        if (u != null && u.getPassword().equals(passwordInput)) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido de nuevo, " + u.getNombre() + "!");
            this.dispose();
            
            abrirPantallaPorRol(u);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarRegistro() {
        String nombre = txtRegNombre.getText().trim();
        String email = txtRegEmail.getText().trim();
        String login = txtRegLogin.getText().trim();
        String password = new String(txtRegPassword.getPassword()).trim();
        String fechaNac = txtRegFechaNac.getText().trim();

        if (nombre.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || fechaNac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llene todos los campos de registro.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (gestorU.buscarUsuario(login) != null) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        int nuevoId = gestorU.generarNuevoId(); 
        
        Cliente nuevoCliente = new Cliente("CLIENTE", nuevoId, nombre, email, login, password, fechaNac, 0, "NONE");

        gestorU.agregarUsuario(nuevoCliente);

        JOptionPane.showMessageDialog(this, "¡Cuenta creada con éxito! Ya puedes iniciar sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        
        txtRegNombre.setText("");
        txtRegEmail.setText("");
        txtRegLogin.setText("");
        txtRegPassword.setText("");
        txtRegFechaNac.setText("");
    }

    private void abrirPantallaPorRol(Usuario u) {
        System.out.println("Abriendo panel para el rol: " + u.getRol());
    }
}