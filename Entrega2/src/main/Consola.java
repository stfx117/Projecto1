package main;

import java.io.IOException;
import java.util.Scanner;
import cafe.BoardGameCafe;
import interfas.GestorUsuario;
import interfas.GestorArchivo;
import users.Administrador;
import users.Cliente;
import users.Cocinero;
import users.Mesero;
import users.Usuario;

public class Consola
{
	private Scanner sc = new Scanner(System.in);
    private BoardGameCafe cafe;
    private GestorUsuario gestorU;
    private GestorArchivo gestorA;
    
    public Consola()
    {
    	this.cafe = BoardGameCafe.getInstancia("BoardGameCafe");
    	this.gestorU = new GestorUsuario();
    	this.gestorA = new GestorArchivo();
    }
    
    public void iniciar() {
        int opcion = 0;
        do{
        	System.out.println("\n--- BIENVENIDO A " + cafe.getNombre().toUpperCase() + " ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse (Nuevo Cliente)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
            	opcion = Integer.parseInt(sc.nextLine());
            	switch (opcion) {
            	case 1:
            		if (realizarLogin()){
            		}
            		break;
            	case 2:
            		registrarNuevoCliente();
            		break;
            	case 3:
            		System.out.println("¡Gracias por visitarnos!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            	}
            } catch(Exception e) {
            	
            }
        	
        } while(opcion != 3);
    }
    
    private void registrarNuevoCliente() {
    	System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
        
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String email = sc.nextLine();
        System.out.print("Cree un nombre de usuario (login): ");
        String login = sc.nextLine();
        System.out.print("Cree una contraseña: ");
        String password = sc.nextLine();
        System.out.print("Ingrese su fecha de nacimiento(formato YYYY-MM-DD): ");
        String fechaNacimiento = sc.nextLine();

        int nuevoId = gestorU.generarNuevoId();
        
        Cliente nuevoCliente = new Cliente("CLIENTE", nuevoId, nombre, email, login, password, fechaNacimiento, 0, "NINGUNO");

        gestorU.agregarUsuario(nuevoCliente);

        try {gestorA.guardarEntidad(nuevoCliente, "usuarios.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println("¡Registro exitoso! Ya puedes iniciar sesión.");
	}

    
	private boolean realizarLogin() {
    	System.out.println("--- LOGIN BOARD GAME CAFE ---");
        System.out.print("Usuario: ");
        String login = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        Usuario u = gestorU.buscarUsuario(login);

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