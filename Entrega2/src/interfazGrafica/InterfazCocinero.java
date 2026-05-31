package interfazGrafica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import interfas.GestorInventario;
import interfas.GestorSugerencias;
import interfas.GestorTurno;
import interfas.GestorUsuario;
import interfas.GestorVenta;

import java.awt.*;
import users.Cocinero;

public class InterfazCocinero extends JFrame {

    private JPanel panelDerechoContenedor;
    private CardLayout cardLayout;
    private JList<String> listaMenu;

    public InterfazCocinero(Cocinero u, GestorInventario gestorI, GestorVenta gestorV, GestorTurno gestorT, GestorSugerencias gestorS) {
        setTitle("Panel de Control - Cocinero");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(220, 0)); 

        String[] opciones = {"Home", "Planear Turno", "Realizar Sugerencia", "Comprar Producto",};
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
        
        PanelTurnoCocinero panelTurno = new PanelTurnoCocinero(u, gestorT);
        PanelSugerenciaCocinero panelSugerencia = new PanelSugerenciaCocinero(u, gestorS);
        PanelCompraCocinero panelCompra = new PanelCompraCocinero(u, gestorV, gestorI);

        panelDerechoContenedor.add(crearPanelGenérico("Bienvenido Cocinero"), "Home");
        panelDerechoContenedor.add(panelTurno, "Planear Turno");
        panelDerechoContenedor.add(panelSugerencia, "Realizar Sugerencia");
        panelDerechoContenedor.add(panelCompra, "Comprar Producto");

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
        etiqueta.setFont(new Font("Arial", Font.BOLD, 35));
        panel.add(etiqueta);
        return panel;
    }

    public static void main(String[] args) {
        Cocinero cocineroPrueba = new Cocinero("COCINERO",3,"Chef Maria","maria@cafe.com","maria","c123","REPOSTERIA",false);
        
        GestorInventario gestorI = new GestorInventario();
        GestorVenta gestorV = new GestorVenta();
        GestorUsuario gestorU = new GestorUsuario();
        GestorTurno gestorT = new GestorTurno(gestorU);
        GestorSugerencias gestorS = new GestorSugerencias(gestorU);
        

        SwingUtilities.invokeLater(() -> {
            new InterfazCocinero(cocineroPrueba, gestorI, gestorV, gestorT, gestorS).setVisible(true);});
    }
}