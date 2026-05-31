package paneles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import administration.Turno; 
import interfas.GestorTurno;
import users.Empleado;

public class PanelTurnoCocinero extends JPanel {
    private GestorTurno gestorT;       
    private Empleado empleado;    

    private JComboBox<String> comboDia;
    private JTextField txtHorario;
    private JButton btnRegistrarTurno;

    public PanelTurnoCocinero(Empleado empleado, GestorTurno gestorT) {
        this.gestorT = gestorT;
        this.empleado = empleado;

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel(" PLANEAR TURNO ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Día de la semana:"), gbc);
        
        String[] diasSemana = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
        comboDia = new JComboBox<>(diasSemana);
        comboDia.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        add(comboDia, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Horario (Ej: 08:00-12:00):"), gbc);
        
        txtHorario = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtHorario, gbc);

        btnRegistrarTurno = new JButton("Guardar Turno");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRegistrarTurno, gbc);

        btnRegistrarTurno.addActionListener(e -> procesarTurno());
    }

    private void procesarTurno() {
        String diaSeleccionado = (String) comboDia.getSelectedItem();
        String horario = txtHorario.getText().trim();

        if (horario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el horario del turno.", "Campo Incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Turno nuevoTurno = new Turno(this.empleado);
        
        nuevoTurno.getDIASEMANAHORA().putIfAbsent(diaSeleccionado, new ArrayList<>());
        nuevoTurno.getDIASEMANAHORA().get(diaSeleccionado).add(horario);

        gestorT.guardarTurno(nuevoTurno); 
        JOptionPane.showMessageDialog(this, "Turno guardado correctamente para el día " + diaSeleccionado + " (" + horario + ").", "Turno Guardado", JOptionPane.INFORMATION_MESSAGE);

        limpiarCampos();
    }

    private void limpiarCampos() {
        comboDia.setSelectedIndex(0); 
        txtHorario.setText("");
    }
}