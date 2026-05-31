package paneles;

import javax.swing.*;

import interfas.GestorInventario;
import interfas.GestorVenta; 

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import sales.Venta;
import products.Producto;
import users.Cliente;

public class PanelCompraCliente extends JPanel {

    private GestorVenta gestorV;       
    private GestorInventario gestorI; 
    private Cliente cliente;    

    private JTextField txtIdProducto;
    private JCheckBox chkPropina;
    private JButton btnRegistrarCompra;

    public PanelCompraCliente(Cliente cliente, GestorVenta gestorV, GestorInventario gestorI) {
        this.gestorV = gestorV;
        this.gestorI = gestorI;
        this.cliente = cliente;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(" NUEVA COMPRA ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("ID del Producto:"), gbc);
        
        txtIdProducto = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtIdProducto, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("¿Desea agregar propina?:"), gbc);
        
        chkPropina = new JCheckBox("Sí, incluir propina");
        chkPropina.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 2;
        add(chkPropina, gbc);

        btnRegistrarCompra = new JButton("Finalizar Compra");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegistrarCompra, gbc);

        btnRegistrarCompra.addActionListener(e -> procesarCompra());
    }

    private void procesarCompra() {
        try {
            String idProductoStr = txtIdProducto.getText().trim();

            if (idProductoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idProducto = Integer.parseInt(idProductoStr);

            Producto producto = gestorI.buscarProductoPorId(idProducto);

            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = gestorV.generarNuevoId(); 

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaActual = LocalDate.now().format(formatoFecha);

            Venta venta = new Venta(id, fechaActual, false);
            venta.agregarProducto(producto);
            venta.calcularPrecio();

            if (chkPropina.isSelected()) {
                venta.aplicarPropina();
            }

            int puntosGanados = (int) venta.getSubtotal() / 1000;
            this.cliente.actualizarPuntos(puntosGanados);

            gestorV.registrarVenta(venta);
            gestorV.actualizarArchivo(); 

            JPanel panelTiquete = new JPanel(new BorderLayout(15, 15));
            panelTiquete.setBackground(Color.WHITE);
            panelTiquete.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

            JPanel panelDatos = new JPanel(new GridLayout(6, 1, 5, 5));
            panelDatos.setBackground(Color.WHITE);
            
            panelDatos.add(new JLabel("Evento: Compra de: " + producto.getNombre())); 
            panelDatos.add(new JLabel("ID Único: " + id));
            panelDatos.add(new JLabel("Fecha realización: " + fechaActual));
            panelDatos.add(new JLabel("Fecha de impresión: " + fechaActual));
            panelDatos.add(new JLabel("Total Pagado: $" + venta.getSubtotal()));
            panelDatos.add(new JLabel("Puntos Ganados: +" + puntosGanados + " (Totales: " + this.cliente.getPuntosFidelidad() + ")"));

            JLabel qR = new JLabel();
            ImageIcon iconOriginal = new ImageIcon("imagenes/qr.png");
            
            if (iconOriginal.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                qR.setIcon(new ImageIcon(imgEscalada));
            }

            panelTiquete.add(panelDatos, BorderLayout.CENTER);
            panelTiquete.add(qR, BorderLayout.EAST);
            
            JLabel lblEncabezado = new JLabel("COMPRA FINALIZADA CON ÉXITO", SwingConstants.CENTER);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
            lblEncabezado.setForeground(new Color(255, 128, 0)); 
            panelTiquete.add(lblEncabezado, BorderLayout.NORTH);

            JOptionPane.showMessageDialog(this, panelTiquete, "Generación de Visualización de Tiquetes", JOptionPane.PLAIN_MESSAGE);          
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El campo numérico debe contener un entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdProducto.setText("");
        chkPropina.setSelected(false);
    }
}