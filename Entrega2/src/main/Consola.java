package main;

import java.io.IOException;
import java.util.Scanner;
import cafe.BoardGameCafe;
import interfas.GestorUsuario;
import interfas.GestorArchivo;
import interfas.GestorInventario;
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
    private GestorInventario gestorI;
    
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
    
    private void registrarNuevoEmpleado() {
    	System.out.println("\n--- REGISTRO DE NUEVO EMPLEADO ---");
        
    	System.out.println("Rol(MESERO o COCINERO):");
    	String rol = sc.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String email = sc.nextLine();
        System.out.print("Cree un nombre de usuario (login): ");
        String login = sc.nextLine();
        System.out.print("Cree una contraseña: ");
        String password = sc.nextLine();
        System.out.print("Codigo de descuento: ");
        String codigo = sc.nextLine();

        int nuevoId = gestorU.generarNuevoId();
        
        Usuario nuevoEmpleado = null;
        
        switch (rol.toUpperCase()) {
        case "MESERO":   
        	nuevoEmpleado = new Mesero(rol, nuevoId, nombre, email, login, password, codigo, false); 
        	break;
        case "COCINERO": 
        	nuevoEmpleado = new Cocinero(rol, nuevoId, nombre, email, login, password, codigo, false); 
        	break;
        }
        gestorU.agregarUsuario(nuevoEmpleado);

        try {gestorA.guardarEntidad(nuevoEmpleado, "usuarios.txt");
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
		int opcion = 0;
		
		do {
			System.out.println("\n--- PANEL DE MESERO ---");
	        System.out.println("1. ");
	        System.out.println("2. ");
	        System.out.println("3. ");
	        System.out.println("4. ");
	        System.out.println("5. Cerrar Sesión");
	        System.out.print("Seleccione una opción: ");
	        
	        try {
            	opcion = Integer.parseInt(sc.nextLine());
            	switch (opcion) {
            	case 1:
            		break;
            	case 2:
            		
            		break;
            	case 3:
            		
                    break;
            	case 4:
            		
            		break;
            	case 5:
            		System.out.println("Sesión cerrada.");
                default:
                    System.out.println("Opción no válida.");
            	}
            } catch(Exception e) {
            	
            }
	        
		}while (opcion != 5);
	}

	private void mostrarAdmin(Administrador u) {
		int opcion = 0;
		
		do {
			System.out.println("\n--- PANEL DE ADMINISTRACIÓN ---");
	        System.out.println("1. Registrar nuevo Empleado (Mesero/Cocinero/)");
	        System.out.println("2. Comprar juego");
	        System.out.println("3. Reparar juego");
	        System.out.println("4. Mover juego");
	        System.out.println("5. Ver solicitudes pendientes");
	        System.out.println("6. Generar Reporte");
	        System.out.println("7. Cerrar Sesión");
	        System.out.print("Seleccione una opción: ");
	        
	        try {
            	opcion = Integer.parseInt(sc.nextLine());
            	switch (opcion) {
            	case 1:
            		registrarNuevoEmpleado();
            		break;
            	case 2:
            		
            		break;
            	case 3:
            		System.out.print("Ingrese el ID del juego a reparar: ");
            	    int idReparar = Integer.parseInt(sc.nextLine());
            	    
            	    u.reparaJuego(gestorI.getInventario(), idReparar);
            	    
            	    gestorI.actualizarArchivoProductos(); 
            	    System.out.println("Juego reparado.");
                    break;
            	case 4:
            		System.out.print("Ingrese el ID del juego a mover a préstamo: ");
            	    int idMover = Integer.parseInt(sc.nextLine());
            	    
            	    u.moverjuego(gestorI.getInventario(), idMover);
            	    
            	    gestorI.actualizarArchivoProductos();
            	    System.out.println("El juego ha sido movido a la sección de préstamos.");
            		break;
            	case 5:
            		System.out.println("--- BUZÓN DE SUGERENCIAS ---");
            	    u.verSugerenciasPendientes();
            		break;
            	case 6:
            		
            		break;
            	case 7:
            		System.out.println("Sesión cerrada.");
                default:
                    System.out.println("Opción no válida.");
            	}
            } catch(Exception e) {
            	
            }
	        
		}while (opcion != 7);
	}
}