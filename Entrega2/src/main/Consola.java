package main;

import java.util.Scanner;
import cafe.BoardGameCafe;
import interfas.GestorUsuario;
import users.Administrador;
import users.Cliente;
import users.Cocinero;
import users.Mesero;
import users.Usuario;

public class Consola
{
	private Scanner sc = new Scanner(System.in);
    private BoardGameCafe cafe;
    private GestorUsuario gestor;
    
    public Consola()
    {
    	this.cafe = BoardGameCafe.getInstancia("BoardGameCafe");
    	this.gestor = new GestorUsuario();
    }
    
    public void iniciar() {
        System.out.println("Bienvenido a " + cafe.getNombre());
        if (realizarLogin()) {
        
        } else {
            System.out.println("Acceso denegado. Cerrando sistema.");
        }
    }
    
    private boolean realizarLogin() {
    	System.out.println("--- LOGIN BOARD GAME CAFE ---");
        System.out.print("Usuario: ");
        String login = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Usuario u = gestor.buscarUsuario(login);

        if (u != null && u.getPassword().equals(pass)) {
            System.out.println("Bienvenido, " + u.getNombre());
            redireccionarPorRol(u);
            return true;
        } else {
            System.out.println("Credenciales incorrectas.");
        }
        return false;
    }
    
    private void redireccionarPorRol(Usuario u) {
        if (u instanceof Administrador) {
            mostrarAdmin((Administrador) u);
        } else if (u instanceof Mesero) {
            mostrarMesero((Mesero) u);
        } else if (u instanceof Cocinero) {
            mostrarCocinero((Cocinero) u);
        } else if (u instanceof Cliente) {
            mostrarCliente((Cliente) u);
        }
    }

	private void mostrarCocinero(Cocinero u) {
		// TODO Auto-generated method stub
		
	}

	private void mostrarCliente(Cliente u) {
		// TODO Auto-generated method stub
		
	}

	private void mostrarMesero(Mesero u) {
		// TODO Auto-generated method stub
		
	}

	private void mostrarAdmin(Administrador u) {
		// TODO Auto-generated method stub
		
	}
}