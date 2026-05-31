package paneles;

import javax.swing.*;

import interfas.GestorInventario;
import interfas.GestorPrestamo;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import services.Mesa;
import services.Prestamo;
import products.Producto;
import products.JuegoDeMesa;
import users.Cliente;

public class PanelPrestamoCliente extends JPanel {

    private GestorPrestamo gestorP;   
    private GestorInventario gestorI; 
    private Cliente cliente;    

    private JTextField txtCapacidadMesa;
    private JTextField txtIdJuego;
    private JButton btnRegistrarPrestamo;

    public PanelPrestamoCliente(Cliente cliente, GestorPrestamo gestorP, GestorInventario gestorI  ) {
        this.gestorP = gestorP;
        this.gestorI = gestorI;
        this.cliente = cliente;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(" NUEVO PRÉSTAMO ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Cantidad personas en mesa:"), gbc);
        
        txtCapacidadMesa = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtCapacidadMesa, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ID del Juego:"), gbc);
        
        txtIdJuego = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtIdJuego, gbc);

        
        btnRegistrarPrestamo = new JButton("Registrar Préstamo");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegistrarPrestamo, gbc);

        
        btnRegistrarPrestamo.addActionListener(e -> procesarPrestamo());
    }

    private void procesarPrestamo() {
        try {
            String capacidadStr = txtCapacidadMesa.getText().trim();
            String idJuegoStr = txtIdJuego.getText().trim();

            if (capacidadStr.isEmpty() || idJuegoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int capacidadMesa = Integer.parseInt(capacidadStr);
            int idJuego = Integer.parseInt(idJuegoStr);

            Producto producto = gestorI.buscarProductoPorId(idJuego);

            if (!(producto instanceof JuegoDeMesa)) {
                JOptionPane.showMessageDialog(this, "El ID ingresado no pertenece a un juego de mesa.", "Error de Tipo", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JuegoDeMesa juego = (JuegoDeMesa) producto;

            int id = gestorP.generarNuevoId(); 

            Mesa mesaPrestamo = new Mesa(id, capacidadMesa, false);
            Prestamo prestamo = new Prestamo(id, mesaPrestamo, false, this.cliente);
            
            prestamo.agregarJuego(juego);

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaImpresion = LocalDate.now().format(formatoFecha); 
            String fechaRealizacion = LocalDate.now().format(formatoFecha); 

            JPanel panelTiquete = new JPanel(new BorderLayout(15, 15));
            panelTiquete.setBackground(Color.WHITE);
            panelTiquete.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

            JPanel panelDatos = new JPanel(new GridLayout(4, 1, 5, 5));
            panelDatos.setBackground(Color.WHITE);
            
            panelDatos.add(new JLabel("Evento: Préstamo de: " + juego.getNombre())); 
            panelDatos.add(new JLabel("ID Único: " + id));
            panelDatos.add(new JLabel("Fecha realización: " + fechaRealizacion));
            panelDatos.add(new JLabel("Fecha de impresión: " + fechaImpresion));

            JLabel qR = new JLabel();
            ImageIcon iconOriginal = new ImageIcon("imagenes/qr.png");
            
            if (iconOriginal.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                qR.setIcon(new ImageIcon(imgEscalada));
            }

            panelTiquete.add(panelDatos, BorderLayout.CENTER);
            panelTiquete.add(qR, BorderLayout.EAST);
            
            JLabel lblEncabezado = new JLabel("TIQUETE DE PRÉSTAMO SEGURO", SwingConstants.CENTER);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
            lblEncabezado.setForeground(new Color(0, 153, 76)); 
            panelTiquete.add(lblEncabezado, BorderLayout.NORTH);

            JOptionPane.showMessageDialog(this, panelTiquete, "Generación de Visualización de Tiquetes", JOptionPane.PLAIN_MESSAGE);          
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los campos numéricos deben contener enteros válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCapacidadMesa.setText("");
        txtIdJuego.setText("");
    }
}