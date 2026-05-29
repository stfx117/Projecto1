package main;

import javax.swing.SwingUtilities;

import interfas.GestorUsuario;
import interfasUsuario.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
    	
        //Consola app = new Consola();
        //app.iniciar();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GestorUsuario gestorU = new GestorUsuario();
                Consola consola = new Consola();
                
                VentanaPrincipal ventana = new VentanaPrincipal(gestorU, consola);
                ventana.setVisible(true);
            }
        });
    }
}
