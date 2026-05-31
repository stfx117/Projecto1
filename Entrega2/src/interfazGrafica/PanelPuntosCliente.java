package interfazGrafica;

import javax.swing.*;
import java.awt.*;

// Importa tus clases lógicas correspondientes
import users.Cliente;

public class PanelPuntosCliente extends JPanel {

    private Cliente cliente;

    public PanelPuntosCliente(Cliente cliente) {
        this.cliente = cliente;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(" PUNTOS DE FIDELIDAD ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(new Color(245, 247, 250)); 
        panelTarjeta.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 2));
        panelTarjeta.setLayout(new GridLayout(2, 1, 10, 10));
        panelTarjeta.setPreferredSize(new Dimension(300, 140));

        JLabel lblSubtitulo = new JLabel("Tus puntos acumulados actuales:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(Color.DARK_GRAY);

        JLabel lblPuntosValor = new JLabel(String.valueOf(this.cliente.getPuntosFidelidad()), SwingConstants.CENTER);
        lblPuntosValor.setFont(new Font("Arial", Font.BOLD, 42)); 
        lblPuntosValor.setForeground(new Color(0, 102, 204));     

        panelTarjeta.add(lblSubtitulo);
        panelTarjeta.add(lblPuntosValor);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelTarjeta, gbc);
    }
}