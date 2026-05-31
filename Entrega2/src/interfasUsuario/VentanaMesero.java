package interfasUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import users.Mesero;
import users.Usuario;
import administration.Turno;
import administration.Sugerencia;
import products.Producto;
import products.JuegoDeMesa;
import interfas.GestorTurno;
import interfas.GestorSugerencias;
import interfas.GestorInventario;
import interfas.GestorUsuario;

public class VentanaMesero extends JFrame {

    private JPanel panelDerechoContenedor;
    private Mesero mesero;
    
    private GestorTurno gestorT;
    private GestorSugerencias gestorS;
    private GestorInventario gestorI;
    private GestorUsuario gestorU;
    private JFrame ventanaLoginOriginal;

    public VentanaMesero(Usuario usuarioLogueado, GestorTurno gestorT, GestorSugerencias gestorS, 
                         GestorInventario gestorI, GestorUsuario gestorU, JFrame loginFrame) {
        
        this.mesero = (Mesero) usuarioLogueado;
        this.gestorT = gestorT;
        this.gestorS = gestorS;
        this.gestorI = gestorI;
        this.gestorU = gestorU;
        this.ventanaLoginOriginal = loginFrame;

        setTitle("Panel de Mesero - " + mesero.getNombre());
        setSize(900, 600); 
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
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBotones.setBackground(new Color(55, 55, 55));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnOp1 = new JButton("1. Planear Turno");
        JButton btnOp2 = new JButton("2. Realizar Sugerencia");
        JButton btnOp3 = new JButton("3. Agregar Juego Dominado");
        JButton btnOp4 = new JButton("4. Compra con Descuento");
        JButton btnOp5 = new JButton("5. Cerrar Sesión");

        JButton[] botones = {btnOp1, btnOp2, btnOp3, btnOp4, btnOp5};
        for (JButton btn : botones) {
            btn.setFocusable(false);
        }

        btnOp1.addActionListener(e -> cambiarPanelContenido(crearPanelPlanearTurno()));
        btnOp2.addActionListener(e -> cambiarPanelContenido(crearPanelSugerencia()));
        btnOp3.addActionListener(e -> cambiarPanelContenido(crearPanelAgregarJuegoLista()));
        btnOp4.addActionListener(e -> cambiarPanelContenido(crearPanelComprarJuegoDescuento()));
        btnOp5.addActionListener(e -> {
            this.dispose();
            if (ventanaLoginOriginal != null) {
                ventanaLoginOriginal.setVisible(true);
            }
        });

        panelBotones.add(btnOp1);
        panelBotones.add(btnOp2);
        panelBotones.add(btnOp3);
        panelBotones.add(btnOp4);
        panelBotones.add(new JLabel("")); 
        panelBotones.add(btnOp5);

        JScrollPane scroll = new JScrollPane(panelBotones);
        scroll.setPreferredSize(new Dimension(230, getHeight()));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private void mostrarBienvenida() {
        JPanel panelBienvenida = new JPanel(new GridBagLayout());
        JLabel lblMsg = new JLabel("<html><center><h2>¡Hola, " + mesero.getNombre() + "!</h2>Área de operaciones de servicio.</center></html>");
        panelBienvenida.add(lblMsg);
        cambiarPanelContenido(panelBienvenida);
    }

    public void cambiarPanelContenido(JPanel panelDestino) {
        panelDerechoContenedor.removeAll();
        panelDerechoContenedor.add(panelDestino, BorderLayout.CENTER);
        panelDerechoContenedor.revalidate();
        panelDerechoContenedor.repaint();
    }

    private JPanel crearPanelPlanearTurno() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        
        Turno nuevoTurno = new Turno(mesero);

        JPanel panelInputs = new JPanel(new GridLayout(3, 2, 5, 5));
        String[] dias = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
        JComboBox<String> cbDia = new JComboBox<>(dias);
        JTextField txtRangoHoras = new JTextField("08:00-12:00");
        JButton btnAgregarDia = new JButton("Agregar Día al Turno");

        panelInputs.add(new JLabel("Seleccione Día:"));        
        panelInputs.add(cbDia);
        panelInputs.add(new JLabel("Rango (ej: 08:00-12:00):")); 
        panelInputs.add(txtRangoHoras);
        panelInputs.add(new JLabel(""));                       
        panelInputs.add(btnAgregarDia);

        JTextArea txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setBorder(BorderFactory.createTitledBorder("Resumen de tu planificación"));
        txtResumen.setText("Aún no has agregado días a este turno.\n");

        JButton btnGuardarTodo = new JButton("Guardar Turno Completo");
        btnGuardarTodo.setBackground(new Color(46, 139, 87));
        btnGuardarTodo.setForeground(Color.WHITE);

        btnAgregarDia.addActionListener(e -> {
            String dia = (String) cbDia.getSelectedItem();
            String rango = txtRangoHoras.getText().trim();

            if (rango.isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "Por favor, defina un rango de horas.");
                return;
            }

            nuevoTurno.getDIASEMANAHORA().putIfAbsent(dia, new ArrayList<>());
            nuevoTurno.getDIASEMANAHORA().get(dia).add(rango);

            StringBuilder sb = new StringBuilder("--- TURNOS REGISTRADOS EN ESTA SESIÓN ---\n");
            nuevoTurno.getDIASEMANAHORA().forEach((key, list) -> {
                sb.append("• ").append(key).append(": ").append(list.toString()).append("\n");
            });
            txtResumen.setText(sb.toString());
            txtRangoHoras.setText("");
        });

        btnGuardarTodo.addActionListener(e -> {
            if (nuevoTurno.getDIASEMANAHORA().isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "Debes agregar al menos un día antes de guardar.");
                return;
            }
            gestorT.guardarTurno(nuevoTurno);
            JOptionPane.showMessageDialog(panelPrincipal, "Turno guardado correctamente en el sistema.");
            mostrarBienvenida();
        });

        panelPrincipal.add(panelInputs, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(txtResumen), BorderLayout.CENTER);
        panelPrincipal.add(btnGuardarTodo, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    private JPanel crearPanelSugerencia() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Escriba su mensaje o sugerencia para el Administrador:"), BorderLayout.NORTH);

        JTextArea txtMensaje = new JTextArea();
        txtMensaje.setLineWrap(true);
        panel.add(new JScrollPane(txtMensaje), BorderLayout.CENTER);

        JButton btnEnviar = new JButton("Enviar Sugerencia Directa");
        panel.add(btnEnviar, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> {
            String msj = txtMensaje.getText().trim();
            if (msj.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "El mensaje no puede estar vacío.");
                return;
            }

            int idSugerencia = gestorS.generarNuevoId();
            Sugerencia nuevaS = new Sugerencia(idSugerencia, mesero, msj);
            
            gestorS.guardarNuevaSugerencia(nuevaS);
            JOptionPane.showMessageDialog(panel, "Sugerencia enviada con éxito. ID: " + idSugerencia);
            mostrarBienvenida();
        });

        return panel;
    }

    private JPanel crearPanelAgregarJuegoLista() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JTextField txtIdJuego = new JTextField();
        JButton btnAgregar = new JButton("Dominar Juego y Añadir a Mi Lista");

        panel.add(new JLabel("Ingrese el ID del juego de mesa que ya domina a la perfección:"));
        panel.add(txtIdJuego);
        panel.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            try {
                int idJuego = Integer.parseInt(txtIdJuego.getText().trim());
                Producto p = gestorI.buscarProductoPorId(idJuego);
                
                if (p instanceof JuegoDeMesa) {
                    mesero.agregarJuegoDominado((JuegoDeMesa) p);
                    gestorU.actualizarArchivo(); 
                    
                    JOptionPane.showMessageDialog(panel, "¡Excelente! El juego '" + p.getNombre() + "' fue agregado a tu lista de maestría.");
                    txtIdJuego.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "El ID no corresponde a un juego de mesa válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Por favor, ingrese un ID numérico válido.", "Formato Inválido", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel crearPanelComprarJuegoDescuento() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JTextField txtCodigo = new JTextField();
        JTextField txtIdJuego = new JTextField();
        JButton btnProcesar = new JButton("Procesar Compra");

        panel.add(new JLabel("Ingrese su Código de Descuento de Empleado:")); panel.add(txtCodigo);
        panel.add(new JLabel("ID del Juego de Mesa a comprar:"));            panel.add(txtIdJuego);
        panel.add(new JLabel(""));                                            panel.add(btnProcesar);

        btnProcesar.addActionListener(e -> {
            String codIngresado = txtCodigo.getText().trim();
            String idString = txtIdJuego.getText().trim();

            if (codIngresado.isEmpty() || idString.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Complete ambos campos para continuar.");
                return;
            }

            if (codIngresado.equalsIgnoreCase("NONE") || codIngresado.equalsIgnoreCase("ninguno")) {
                JOptionPane.showMessageDialog(panel, "Código inválido. No se puede proceder con el descuento de empleado.", "Denegado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idC = Integer.parseInt(idString);
                Producto prod = gestorI.buscarProductoPorId(idC);

                if (prod != null) {
                    double precioFinal = prod.getPrecioBase() * 0.80; 
                    
                    String recibo = "¡Código Válido Aplicado!\n\n"
                                  + "Juego: " + prod.getNombre() + "\n"
                                  + "Precio Original: $" + prod.getPrecioBase() + "\n"
                                  + "Precio Empleado (-20%): $" + precioFinal + "\n\n"
                                  + "Compra procesada con éxito.";
                    
                    JOptionPane.showMessageDialog(panel, recibo, "Ticket de Compra", JOptionPane.INFORMATION_MESSAGE);
                    txtCodigo.setText("");
                    txtIdJuego.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "El ID del producto no existe en el inventario.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "El ID del juego debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}