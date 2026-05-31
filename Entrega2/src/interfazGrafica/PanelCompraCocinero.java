package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import interfas.GestorInventario;
import interfas.GestorVenta;

import sales.Venta;
import products.Producto;
import users.Empleado; 

public class PanelCompraCocinero extends JPanel {

    private GestorVenta gestorV;       
    private GestorInventario gestorI; 
    private Empleado empleado;

    private JTextField txtCodigoDescuento;
    private JTextField txtIdProducto;
    private JButton btnRegistrarCompra;

    public PanelCompraCocinero(Empleado empleado, GestorVenta gestorV, GestorInventario gestorI) {
        this.gestorV = gestorV;
        this.gestorI = gestorI;
        this.empleado = empleado;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel lblTitulo = new JLabel(" COMPRA CON DESCUENTO ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;


        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Código de Descuento:"), gbc);
        
        txtCodigoDescuento = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtCodigoDescuento, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("ID del Producto:"), gbc);
        
        txtIdProducto = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtIdProducto, gbc);

        
        btnRegistrarCompra = new JButton("Finalizar Compra");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegistrarCompra, gbc);

        btnRegistrarCompra.addActionListener(e -> procesarCompraDescuento());
    }

    private void procesarCompraDescuento() {
        try {
            String codigoIngresado = txtCodigoDescuento.getText().trim();
            String idProductoStr = txtIdProducto.getText().trim();

            if (codigoIngresado.isEmpty() || idProductoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!codigoIngresado.equalsIgnoreCase(empleado.getCodigoDescuento())) {
                JOptionPane.showMessageDialog(this, "Código inválido. No se puede procesar la compra.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProducto = Integer.parseInt(idProductoStr);


            Producto producto = gestorI.buscarProductoPorId(idProducto);

            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            int idVentaAleatorio = gestorV.generarNuevoId(); 
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaActual = LocalDate.now().format(formatoFecha);

            Venta venta = new Venta(idVentaAleatorio, fechaActual, false);
            venta.agregarProducto(producto);
            venta.calcularPrecio();

            double subtotalOriginal = venta.getSubtotal();
            double totalConDescuento = subtotalOriginal * 0.80;
            

            venta.setSubtotal(totalConDescuento);

            gestorV.agregarVenta(venta);
            gestorV.actualizarArchivo();


            JPanel panelTiquete = new JPanel(new BorderLayout(15, 15));
            panelTiquete.setBackground(Color.WHITE);
            panelTiquete.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

            JPanel panelDatos = new JPanel(new GridLayout(6, 1, 5, 5));
            panelDatos.setBackground(Color.WHITE);
            
            panelDatos.add(new JLabel("Evento: Compra Empleado - " + producto.getNombre())); 
            panelDatos.add(new JLabel("ID Único Venta: " + idVentaAleatorio));
            panelDatos.add(new JLabel("Fecha realización: " + fechaActual));
            panelDatos.add(new JLabel("Fecha de impresión: " + fechaActual));
            panelDatos.add(new JLabel("Subtotal Original: " + subtotalOriginal));
            panelDatos.add(new JLabel("Total con Descuento (20%): " + totalConDescuento));

            JLabel qR = new JLabel();
            ImageIcon iconOriginal = new ImageIcon("imagenes/qr.png");
            
            if (iconOriginal.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                qR.setIcon(new ImageIcon(imgEscalada));
            }

            panelTiquete.add(panelDatos, BorderLayout.CENTER);
            panelTiquete.add(qR, BorderLayout.EAST);
            
            JLabel lblEncabezado = new JLabel("COMPRA CON DESCUENTO FINALIZADA", SwingConstants.CENTER);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
            lblEncabezado.setForeground(new Color(255, 128, 0));
            panelTiquete.add(lblEncabezado, BorderLayout.NORTH);

            JOptionPane.showMessageDialog(this, panelTiquete, "Generación de Visualización de Tiquetes", JOptionPane.PLAIN_MESSAGE);          
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID del producto debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCodigoDescuento.setText("");
        txtIdProducto.setText("");
    }
}