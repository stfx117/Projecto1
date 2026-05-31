package paneles;

import javax.swing.*;
import java.awt.*;

import administration.Sugerencia; 
import interfas.GestorSugerencias;
import users.Empleado;

public class PanelSugerenciaCocinero extends JPanel {

    private GestorSugerencias gestorS;       
    private Empleado empleado;  

    private JTextArea txtSugerencia;
    private JButton btnEnviarSugerencia;

    public PanelSugerenciaCocinero(Empleado empleado, GestorSugerencias gestorS) {
        this.gestorS = gestorS;
        this.empleado = empleado;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(" SUGERENCIA DE PRODUCTO ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(new JLabel("Escriba la sugerencia o propuesta de producto aquí:"), gbc);

        txtSugerencia = new JTextArea(6, 25);
        txtSugerencia.setLineWrap(true);     
        txtSugerencia.setWrapStyleWord(true);
        txtSugerencia.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JScrollPane scrollPane = new JScrollPane(txtSugerencia);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; 
        add(scrollPane, gbc);

        btnEnviarSugerencia = new JButton("Enviar Sugerencia");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnEnviarSugerencia, gbc);

        btnEnviarSugerencia.addActionListener(e -> procesarSugerencia());
    }

    private void procesarSugerencia() {
        String msj = txtSugerencia.getText().trim();
        if (msj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, escriba un mensaje antes de enviar.", "Campo Incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = gestorS.generarNuevoId();
        Sugerencia nuevaS = new Sugerencia(id, this.empleado, msj);
        gestorS.guardarNuevaSugerencia(nuevaS); 
        JOptionPane.showMessageDialog(this, "Sugerencia enviada con éxito.", "Envío Exitoso", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtSugerencia.setText("");
    }
}