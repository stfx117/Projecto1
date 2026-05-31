package interfasUsuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import users.Administrador;
import users.Cocinero;
import users.Mesero;
import users.Usuario;
import products.JuegoDeMesa;
import administration.Sugerencia;
import interfas.GestorInventario;
import interfas.GestorUsuario;
import interfas.GestorSugerencias;
import interfas.GestorHistorial;

public class VentanaAdministrador extends JFrame {
    
    private JPanel panelDerechoContenedor;
    private Administrador admin;
    
    private GestorInventario gestorI;
    private GestorUsuario gestorU;
    private GestorSugerencias gestorS;
    private GestorHistorial gestorH;
    private JFrame ventanaLoginOriginal;

    public VentanaAdministrador(Usuario usuarioLogueado, GestorInventario gestorI, GestorUsuario gestorU, 
                                GestorSugerencias gestorS, GestorHistorial gestorH, JFrame loginFrame) {
        
        this.admin = (Administrador) usuarioLogueado;
        this.gestorI = gestorI;
        this.gestorU = gestorU;
        this.gestorS = gestorS;
        this.gestorH = gestorH;
        this.ventanaLoginOriginal = loginFrame;

        setTitle("Panel de Administración - " + admin.getNombre());
        setSize(950, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout()); 

        add(crearBarraLateral(), BorderLayout.WEST);

        panelDerechoContenedor = new JPanel(new BorderLayout());
        panelDerechoContenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(panelDerechoContenedor, BorderLayout.CENTER);

        mostrarBienvenida();
    }

    private JScrollPane crearBarraLateral() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(43, 43, 43));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMenu = new JLabel("MENÚ ADMIN");
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 14));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotones.add(lblMenu);
        panelBotones.add(Box.createVerticalStrut(20));

        JButton btnOp1 = new JButton("1. Registrar Empleado");
        JButton btnOp2 = new JButton("2. Comprar Juego");
        JButton btnOp3 = new JButton("3. Reparar Juego");
        JButton btnOp4 = new JButton("4. Mover Juego");
        JButton btnOp5 = new JButton("5. Solicitudes Pendientes");
        JButton btnOp6 = new JButton("6. Generar Reporte");
        JButton btnOp7 = new JButton("7. Cerrar Sesión");

        JButton[] botones = {btnOp1, btnOp2, btnOp3, btnOp4, btnOp5, btnOp6, btnOp7};
        Dimension dimBoton = new Dimension(200, 40);

        for (JButton btn : botones) {
            btn.setMaximumSize(dimBoton);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusable(false);
            panelBotones.add(btn);
            panelBotones.add(Box.createVerticalStrut(10));
        }

        btnOp1.addActionListener(e -> cambiarPanelContenido(crearPanelRegistrarEmpleado()));
        btnOp2.addActionListener(e -> cambiarPanelContenido(crearPanelComprarJuego()));
        btnOp3.addActionListener(e -> cambiarPanelContenido(crearPanelRepararJuego()));
        btnOp4.addActionListener(e -> cambiarPanelContenido(crearPanelMoverJuego()));
        btnOp5.addActionListener(e -> cambiarPanelContenido(crearPanelSugerencias()));
        btnOp6.addActionListener(e -> cambiarPanelContenido(crearPanelReporte()));
        
        btnOp7.addActionListener(e -> {
            this.dispose();
            if (ventanaLoginOriginal != null) {
                ventanaLoginOriginal.setVisible(true);
            }
        });

        JScrollPane scroll = new JScrollPane(panelBotones);
        scroll.setPreferredSize(new Dimension(220, getHeight()));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private void mostrarBienvenida() {
        JPanel panelBienvenida = new JPanel(new GridBagLayout());
        JLabel lblMsg = new JLabel("<html><center><h2>Sistema Board Game Café</h2>Seleccione una opción del menú izquierdo para comenzar.</center></html>");
        panelBienvenida.add(lblMsg);
        cambiarPanelContenido(panelBienvenida);
    }

    public void cambiarPanelContenido(JPanel panelDestino) {
        panelDerechoContenedor.removeAll();
        panelDerechoContenedor.add(panelDestino, BorderLayout.CENTER);
        panelDerechoContenedor.revalidate();
        panelDerechoContenedor.repaint();
    }

    private JPanel crearPanelRegistrarEmpleado() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); 

        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtLogin = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtCodigoDescuento = new JTextField(); 
        JComboBox<String> cbRol = new JComboBox<>(new String[]{"MESERO", "COCINERO"});
        JButton btnGuardar = new JButton("Registrar Empleado");

        panel.add(new JLabel("Nombre Completo:")); 
        panel.add(txtNombre);
        panel.add(new JLabel("Email:"));           
        panel.add(txtEmail);
        panel.add(new JLabel("Usuario (Login):"));  
        panel.add(txtLogin);
        panel.add(new JLabel("Contraseña:"));      
        panel.add(txtPassword);
        panel.add(new JLabel("Código Descuento:")); 
        panel.add(txtCodigoDescuento);
        panel.add(new JLabel("Rol del Empleado:")); 
        panel.add(cbRol);
        
        panel.add(new JLabel(""));                  
        panel.add(btnGuardar); 

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String login = txtLogin.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String codigoDesc = txtCodigoDescuento.getText().trim(); 
            String rolSeleccionado = (String) cbRol.getSelectedItem();

            if (nombre.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || codigoDesc.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Por favor, complete todos los campos.");
                return;
            }

            if (gestorU.buscarUsuario(login) != null) {
                JOptionPane.showMessageDialog(panel, "El usuario ya existe.");
                return;
            }

            int nuevoId = gestorU.generarNuevoId();
            Usuario nuevoEmpleado = null;

            if (rolSeleccionado.equals("MESERO")) {
                nuevoEmpleado = new Mesero(rolSeleccionado, nuevoId, nombre, email, login, password, codigoDesc, false);
            } else {
                nuevoEmpleado = new Cocinero(rolSeleccionado, nuevoId, nombre, email, login, password, codigoDesc, false);
            }

            gestorU.agregarUsuario(nuevoEmpleado);
            JOptionPane.showMessageDialog(panel, "¡Empleado registrado! ID: " + nuevoId);
            
            txtNombre.setText(""); 
            txtEmail.setText(""); 
            txtLogin.setText(""); 
            txtPassword.setText(""); 
            txtCodigoDescuento.setText("");
        });

        return panel;
    }

    private JPanel crearPanelComprarJuego() {
        JPanel panel = new JPanel(new GridLayout(11, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JTextField txtNom = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtEmp = new JTextField();
        JTextField txtMinJ = new JTextField();
        JTextField txtMaxJ = new JTextField();

        JComboBox<JuegoDeMesa.restriccionEdad> cbRes = new JComboBox<>(JuegoDeMesa.restriccionEdad.values());
        JComboBox<JuegoDeMesa.Categoria> cbCat = new JComboBox<>(JuegoDeMesa.Categoria.values());

        JCheckBox chkDif = new JCheckBox("¿Es difícil?");
        JCheckBox chkParaV = new JCheckBox("¿Es para la venta?");
        
        JButton btnGuardarJuego = new JButton("Registrar Compra");

        panel.add(new JLabel("Nombre:"));           
        panel.add(txtNom);
        panel.add(new JLabel("Precio Base:"));      
        panel.add(txtPrecio);
        panel.add(new JLabel("Descripción:"));      
        panel.add(txtDesc);
        panel.add(new JLabel("Año Publicación:"));  
        panel.add(txtAnio);
        panel.add(new JLabel("Empresa:"));          
        panel.add(txtEmp);
        panel.add(new JLabel("Min Jugadores:"));    
        panel.add(txtMinJ);
        panel.add(new JLabel("Max Jugadores:"));    
        panel.add(txtMaxJ);
        panel.add(new JLabel("Restricción Edad:")); 
        panel.add(cbRes);
        panel.add(new JLabel("Categoría:"));        
        panel.add(cbCat);
        
        panel.add(chkDif);                          
        panel.add(chkParaV);
        
        panel.add(new JLabel(""));                  
        panel.add(btnGuardarJuego);

        btnGuardarJuego.addActionListener(e -> {
            try {
                String nom = txtNom.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String desc = txtDesc.getText().trim();
                int anio = Integer.parseInt(txtAnio.getText().trim());
                String emp = txtEmp.getText().trim();
                int minJ = Integer.parseInt(txtMinJ.getText().trim());
                int maxJ = Integer.parseInt(txtMaxJ.getText().trim());
                JuegoDeMesa.restriccionEdad res = (JuegoDeMesa.restriccionEdad) cbRes.getSelectedItem();
                JuegoDeMesa.Categoria cat = (JuegoDeMesa.Categoria) cbCat.getSelectedItem();
                boolean dif = chkDif.isSelected();
                boolean paraV = chkParaV.isSelected();

                int nuevoId = gestorI.generarNuevoIdProducto();

                admin.comprarJuego(gestorI.getInventario(), nuevoId, nom, precio, desc, anio, emp, 
                                   minJ, maxJ, res, dif, JuegoDeMesa.EstadoFisico.NUEVO, cat, paraV);
                
                gestorI.actualizarArchivoProductos();

                JOptionPane.showMessageDialog(panel, "Juego comprado y registrado con ID: " + nuevoId, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mostrarBienvenida(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error en los datos numéricos introducidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel crearPanelRepararJuego() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("ID del juego a reparar:"));
        JTextField txtId = new JTextField(10);
        panel.add(txtId);
        JButton btnReparar = new JButton("Reparar");
        panel.add(btnReparar);

        btnReparar.addActionListener(e -> {
            try {
                int idReparar = Integer.parseInt(txtId.getText().trim());
                admin.reparaJuego(gestorI.getInventario(), idReparar);
                gestorI.actualizarArchivoProductos();
                JOptionPane.showMessageDialog(panel, "Juego enviado al taller y marcado como Reparado.");
                txtId.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel crearPanelMoverJuego() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("ID del juego a mover a préstamo:"));
        JTextField txtId = new JTextField(10);
        panel.add(txtId);
        JButton btnMover = new JButton("Mover");
        panel.add(btnMover);

        btnMover.addActionListener(e -> {
            try {
                int idMover = Integer.parseInt(txtId.getText().trim());
                admin.moverjuego(gestorI.getInventario(), idMover);
                gestorI.actualizarArchivoProductos();
                JOptionPane.showMessageDialog(panel, "El juego ha sido movido a la sección de préstamos.");
                txtId.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel crearPanelSugerencias() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        panel.add(new JScrollPane(txtArea), BorderLayout.CENTER);

        StringBuilder sb = new StringBuilder("--- SUGERENCIAS PENDIENTES ---\n\n");
        List<Sugerencia> lista = gestorS.getSugerencias();
        
        if (lista == null || lista.isEmpty()) {
            sb.append("No hay ninguna sugerencia registrada en el sistema.");
        } else {
            boolean hayPendientes = false;
            for (Sugerencia s : lista) {
                if (!s.isLeida()) {
                    sb.append("ID: ").append(s.getId()).append(" | De: ").append(s.getEmpleado().getNombre()).append("\n");
                    sb.append("Mensaje: ").append(s.getMensaje()).append("\n");
                    sb.append("---------------------------------------------------\n");
                    s.setLeida(true);
                    hayPendientes = true;
                }
            }
            if (!hayPendientes) {
                sb.append("No hay sugerencias nuevas.");
            } else {
                gestorS.actualizarArchivo(); 
            }
        }
        txtArea.setText(sb.toString());
        return panel;
    }

    private JPanel crearPanelReporte() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel panelInputs = new JPanel(new FlowLayout());
        
        JTextField txtInicio = new JTextField("2026-01-01", 10);
        JTextField txtFin = new JTextField("2026-12-31", 10);
        JButton btnCalcular = new JButton("Calcular Informe");
        
        panelInputs.add(new JLabel("Inicio (AAAA-MM-DD):"));
        panelInputs.add(txtInicio);
        panelInputs.add(new JLabel("Fin (AAAA-MM-DD):"));
        panelInputs.add(txtFin);
        panelInputs.add(btnCalcular);
        
        panel.add(panelInputs, BorderLayout.NORTH);
        
        JTextArea txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        btnCalcular.addActionListener(e -> {
            try {
                LocalDate inicio = LocalDate.parse(txtInicio.getText().trim());
                LocalDate fin = LocalDate.parse(txtFin.getText().trim());

                Map<String, Double> datos = admin.informeEstado(gestorH.getHistorial(), inicio, fin);

                StringBuilder res = new StringBuilder();
                res.append("=========================================\n");
                res.append("       RESULTADOS DEL PERIODO SELECCIONADO\n");
                res.append("=========================================\n");
                res.append(String.format(" Ingresos por Juegos:  $%.2f\n", datos.get("juegos")));
                res.append(String.format(" Ingresos por Comida:  $%.2f\n", datos.get("comida")));
                res.append(String.format(" Impuestos (IVA):      $%.2f\n", datos.get("impuestos")));
                res.append(String.format(" Propinas:             $%.2f\n", datos.get("propinas")));
                res.append("-----------------------------------------\n");
                res.append(String.format(" TOTAL NETO:           $%.2f\n", datos.get("totalNeto")));
                res.append("=========================================");

                txtResultado.setText(res.toString());
            } catch (Exception ex) {
                txtResultado.setText("Error: Formato de fecha inválido o datos insuficientes.");
            }
        });

        return panel;
    }
}