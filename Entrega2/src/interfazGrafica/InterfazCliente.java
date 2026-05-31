package interfazGrafica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import interfas.GestorInventario;
import interfas.GestorPrestamo;
import interfas.GestorReserva;
import interfas.GestorVenta;
import users.Cliente;

import java.awt.*;

public class InterfazCliente extends JFrame {

    private JPanel panelDerechoContenedor;
    private CardLayout cardLayout;
    private JList<String> listaMenu;

    public InterfazCliente(Cliente u, GestorReserva gestorR, GestorPrestamo gestorP, GestorInventario gestorI, GestorVenta gestorV) {
        setTitle("Panel de Control - Cliente");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(220, 0)); 

        String[] opciones = {"Home", "Realizar Reserva", "Solicitar Préstamo", "Realizar Compra", "Ver Puntos de Fidelidad"};
        listaMenu = new JList<>(opciones);        

        listaMenu.setBackground(new Color(43, 43, 43)); 
        listaMenu.setForeground(Color.WHITE); 
        listaMenu.setFont(new Font("Arial", Font.BOLD, 14));
        listaMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMenu.setSelectionBackground(new Color(70, 70, 70)); 
        listaMenu.setSelectionForeground(Color.CYAN); 


        panelIzquierdo.add(listaMenu, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        panelDerechoContenedor = new JPanel(cardLayout);

        PanelReservaCliente panelReserva = new PanelReservaCliente(u, gestorR);
        PanelPrestamoCliente panelPrestamo = new PanelPrestamoCliente(u, gestorP, gestorI);
        PanelCompraCliente panelCompra = new PanelCompraCliente(u, gestorV, gestorI);
        PanelPuntosCliente panelPuntos = new PanelPuntosCliente(u);
        
        panelDerechoContenedor.add(crearPanelGenérico("Bienvenido a la Aplicación"), "Home");
        panelDerechoContenedor.add(panelReserva, "Realizar Reserva");
        panelDerechoContenedor.add(panelPrestamo, "Solicitar Préstamo");
        panelDerechoContenedor.add(panelCompra, "Realizar Compra");
        panelDerechoContenedor.add(panelPuntos, "Ver Puntos de Fidelidad");

        listaMenu.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String opcionSeleccionada = listaMenu.getSelectedValue();
                    cardLayout.show(panelDerechoContenedor, opcionSeleccionada);
                }
            }
        });

        listaMenu.setSelectedIndex(0);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerechoContenedor, BorderLayout.CENTER);
    }

    private JPanel crearPanelGenérico(String texto) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(etiqueta);
        return panel;
    }

    //public static void main(String[] args) {
    	//Cliente cliente = new Cliente("CLIENTE",4,"Lucas Gomez","lucas@mail.com","lucas","l123","1995-05-10",0,"NINGUNO");
    //	GestorReserva gestorR = new GestorReserva();
    	//GestorPrestamo gestorP = new GestorPrestamo();
    //	GestorInventario gestorI = new GestorInventario();
    //	GestorVenta gestorV = new GestorVenta();
    //    SwingUtilities.invokeLater(() -> new InterfazCliente(cliente, gestorR, gestorP, gestorI, gestorV).setVisible(true));
    //}
}