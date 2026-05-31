package interfasUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfas.GestorUsuario;
import interfas.GestorHistorial;
import main.Consola;
import users.Usuario;
import users.Cliente;
import users.Cocinero; 

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
    	JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));

        txtLoginUsuario = new JTextField(15);
        txtLoginPassword = new JPasswordField(15);
        btnIngresar = new JButton("Ingresar al Sistema");

        formulario.add(new JLabel("Usuario (Login):")); 
        formulario.add(txtLoginUsuario);
        
        formulario.add(new JLabel("Contraseña:"));      
        formulario.add(txtLoginPassword);
        
        formulario.add(new JLabel(""));                 
        formulario.add(btnIngresar);

        btnIngresar.addActionListener(e -> procesarLogin());


        JPanel contenedorPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        contenedorPrincipal.add(formulario); 

        return contenedorPrincipal;
    }

    private JPanel crearPanelRegistro() {
    	JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtRegNombre = new JTextField(15);
        txtRegEmail = new JTextField(15);
        txtRegLogin = new JTextField(15);
        txtRegPassword = new JPasswordField(15);
        txtRegFechaNac = new JTextField(15);
        btnRegistrar = new JButton("Crear Cuenta de Cliente");

        panel.add(new JLabel("Nombre Completo:"));           
        panel.add(txtRegNombre);
        panel.add(new JLabel("Email:"));                     
        panel.add(txtRegEmail);
        panel.add(new JLabel("Nombre de Usuario (Login):")); 
        panel.add(txtRegLogin);
        panel.add(new JLabel("Contraseña:"));               
        panel.add(txtRegPassword);
        panel.add(new JLabel("F. Nacimiento (AAAA-MM-DD):")); 
        panel.add(txtRegFechaNac);
        
        panel.add(new JLabel(""));                           
        panel.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> procesarRegistro());

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
    	String rol = u.getRol().toString().toUpperCase(); 
        
        switch (rol) {
            case "ADMIN":
            case "ADMINISTRADOR":
      
            	VentanaAdministrador vAdmin = new VentanaAdministrador(
            		    u, 
            		    consolaBase.getGestorI(), 
            		    this.gestorU, 
            		    consolaBase.getGestorS(), 
            		    consolaBase.getGestorH(), 
            		    this 
            		);
            		vAdmin.setVisible(true); 
                break;
                
            case "COCINERO":
                Cocinero cocineroActual = (Cocinero) u;
                InterfazCocinero vCocinero = new InterfazCocinero(
                    cocineroActual,
                    consolaBase.getGestorI(), 
                    consolaBase.getGestorV(), 
                    consolaBase.getGestorT(), 
                    consolaBase.getGestorS()  
                );
                

                vCocinero.setVisible(true);
                break;
                
            case "MESERO":
            	VentanaMesero vMesero = new VentanaMesero(
            	        u, 
            	        consolaBase.getGestorT(), 
            	        consolaBase.getGestorS(), 
            	        consolaBase.getGestorI(), 
            	        this.gestorU,             
            	        this                      
            	    );
            	    vMesero.setVisible(true);
                break;
                
            case "CLIENTE":

                Cliente clienteActual = (Cliente) u;
                

                InterfazCliente vCliente = new InterfazCliente(
                    clienteActual,
                    consolaBase.getGestorR(), // GestorReserva
                    consolaBase.getGestorP(), // GestorPrestamo
                    consolaBase.getGestorI(), // GestorInventario
                    consolaBase.getGestorV()  // GestorVenta
                );
                
                vCliente.setVisible(true);
                break;
                
            default:
                JOptionPane.showMessageDialog(this, "El rol '" + rol + "' no tiene una interfaz asignada.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}