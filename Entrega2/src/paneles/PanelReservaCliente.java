package paneles;

import javax.swing.*;

import services.Mesa;
import services.Reserva;
import users.Cliente;
import interfas.GestorReserva;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PanelReservaCliente extends JPanel {
    private JTextField txtfecha;
    private JTextField txtPersonas;
    private JCheckBox chkMenores;
    private JCheckBox chkJovenes;
    private JTextField txtEstadia;
    private JButton btnRegistrar;
    private Cliente cliente;
    private GestorReserva gestorR;
    
    public PanelReservaCliente(Cliente u, GestorReserva gestorR) {
        
    	cliente = u;
    	this.gestorR = gestorR;  
        setBackground(Color.WHITE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel lblTitulo = new JLabel(" NUEVA RESERVA ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 3; 
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Cantidad personas:"), gbc);
        
        txtPersonas = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtPersonas, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("¿Hay menores de 5 años?:"), gbc);
        
        chkMenores = new JCheckBox("Sí");
        chkMenores.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 3;
        add(chkMenores, gbc);

        
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("¿Hay jóvenes?:"), gbc);
        
        chkJovenes = new JCheckBox("Sí");
        chkJovenes.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 4;
        add(chkJovenes, gbc);

        
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Tiempo de estadía:"), gbc);
        
        txtEstadia = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 5;
        add(txtEstadia, gbc);

        
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Dia de la reserva: "), gbc);
        
        txtfecha = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 6;
        add(txtfecha, gbc);
        
        btnRegistrar = new JButton("Registrar Reserva");
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> procesarReserva());
    }

    private void procesarReserva() {
        try {
            String personasStr = txtPersonas.getText().trim();
            String estadia = txtEstadia.getText().trim();
            String fechaRealizacion = txtfecha.getText().trim();

            if (personasStr.isEmpty() || estadia.isEmpty() || fechaRealizacion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
        	
            int personas = Integer.parseInt(txtPersonas.getText().trim());
            boolean menores = chkMenores.isSelected(); 
            boolean jovenes = chkJovenes.isSelected(); 
            int id = this.gestorR.generarNuevoId();
            

            if (estadia.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el tiempo de estadía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Mesa mesaReserva = new Mesa(id, personas, false);
			Reserva reserva = new Reserva(id, this.cliente, mesaReserva, personas, menores, jovenes, estadia);
			
			this.gestorR.registrarReserva(reserva);
            this.gestorR.actualizarArchivo();

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaImpresion = LocalDate.now().format(formatoFecha); 
            
            JPanel panelTiquete = new JPanel(new BorderLayout(15, 15));
            panelTiquete.setBackground(Color.WHITE);
            panelTiquete.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

            JPanel panelDatos = new JPanel(new GridLayout(4, 1, 5, 5));
            panelDatos.setBackground(Color.WHITE);
            
            panelDatos.add(new JLabel("Para una reserva"));
            panelDatos.add(new JLabel("ID Único: " + id));
            panelDatos.add(new JLabel("Fecha realización: " + fechaRealizacion));
            panelDatos.add(new JLabel("Fecha de impresión: " + fechaImpresion));

            JLabel qR = new JLabel();
            String rutaImagen = "imagenes/qr.png";
            ImageIcon iconOriginal = new ImageIcon(rutaImagen);
            
            if (iconOriginal.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                qR.setIcon(new ImageIcon(imgEscalada));
            }
            
            panelTiquete.add(panelDatos, BorderLayout.CENTER);
            panelTiquete.add(qR, BorderLayout.EAST);            

            JLabel lblEncabezado = new JLabel("TIQUETE DE CONFIRMACIÓN", SwingConstants.CENTER);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
            lblEncabezado.setForeground(Color.black);
            panelTiquete.add(lblEncabezado, BorderLayout.NORTH);

            JOptionPane.showMessageDialog(this, panelTiquete, "Generación de Visualización de Tiquetes", JOptionPane.PLAIN_MESSAGE);
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad de personas deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtPersonas.setText("");
        chkMenores.setSelected(false);
        chkJovenes.setSelected(false);
        txtEstadia.setText("");
    }
}