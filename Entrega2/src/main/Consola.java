package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import administration.Sugerencia;
import cafe.BoardGameCafe;
import interfas.GestorUsuario;
import products.JuegoDeMesa;
import interfas.GestorArchivo;
import interfas.GestorInventario;
import interfas.GestorHistorial;
import interfas.GestorSugerencias;
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
    private GestorHistorial gestorH;
    private GestorSugerencias gestorS;
    
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
            		System.out.println("\n--- COMPRA DE NUEVO JUEGO DE MESA ---");
            	    
            	    System.out.print("Nombre: "); String nom = sc.nextLine();
            	    System.out.print("Precio Base: "); double precio = Double.parseDouble(sc.nextLine());
            	    System.out.print("Descripción: "); String desc = sc.nextLine();
            	    System.out.print("Año Publicación: "); int anio = Integer.parseInt(sc.nextLine());
            	    System.out.print("Empresa: "); String emp = sc.nextLine();
            	    System.out.print("Min Jugadores: "); int minJ = Integer.parseInt(sc.nextLine());
            	    System.out.print("Max Jugadores: "); int maxJ = Integer.parseInt(sc.nextLine());

            	    System.out.println("Restricción de Edad (1. TODOS, 2. MAYORES_5, 3. ADULTOS): ");
            	    int resOpt = Integer.parseInt(sc.nextLine());
            	    JuegoDeMesa.restriccionEdad res = (resOpt == 1) ? JuegoDeMesa.restriccionEdad.TODOS : 
            	                                      (resOpt == 2) ? JuegoDeMesa.restriccionEdad.MAYORES_5 : 
            	                                                      JuegoDeMesa.restriccionEdad.ADULTOS;

            	    System.out.println("Categoría (1. CARTAS, 2. TABLERO, 3. ACCION): ");
            	    int catOpt = Integer.parseInt(sc.nextLine());
            	    JuegoDeMesa.Categoria cat = (catOpt == 1) ? JuegoDeMesa.Categoria.CARTAS : 
            	                                (catOpt == 2) ? JuegoDeMesa.Categoria.TABLERO : 
            	                                                JuegoDeMesa.Categoria.ACCION;

            	    System.out.print("¿Es difícil? (true/false): "); boolean dif = Boolean.parseBoolean(sc.nextLine());
            	    System.out.print("¿Es para la venta? (true/false): "); boolean paraV = Boolean.parseBoolean(sc.nextLine());

            	    int nuevoId = gestorI.generarNuevoIdProducto();

            	    u.comprarJuego(gestorI.getInventario(), nuevoId, nom, precio, desc, anio, emp, 
            	                   minJ, maxJ, res, dif, JuegoDeMesa.EstadoFisico.NUEVO, cat, paraV);

            	    gestorI.actualizarArchivoProductos();

            	    System.out.println("Juego comprado y registrado con ID: " + nuevoId);
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
            		System.out.println("\n--- SOLICITUDES PENDIENTES ---");
            	    List<Sugerencia> lista = gestorS.getSugerencias();
            	    boolean hayPendientes = false;

            	    for (Sugerencia s : lista) {
            	        if (!s.isLeida()) {
            	            System.out.println("ID: " + s.getId() + " | De: " + s.getEmpleado().getNombre());
            	            System.out.println("Mensaje: " + s.getMensaje());
            	            System.out.println("-----------------------------------");
            	            s.setLeida(true);
            	            hayPendientes = true;
            	        }
            	    }

            	    if (!hayPendientes) {
            	        System.out.println("No hay sugerencias nuevas.");
            	    } else {
            	        gestorS.actualizarArchivo();
            	    }
            	    break;
            	case 6:
            		try {
            	        System.out.print("Ingrese fecha inicio (AAAA-MM-DD): ");
            	        LocalDate inicio = LocalDate.parse(sc.nextLine());
            	        System.out.print("Ingrese fecha fin (AAAA-MM-DD): ");
            	        LocalDate fin = LocalDate.parse(sc.nextLine());

            	        Map<String, Double> datos = u.informeEstado(gestorH.getHistorial(), inicio, fin);

            	        System.out.println("\n--- RESULTADOS DEL PERIODO ---");
            	        System.out.println("Ingresos por Juegos: $" + datos.get("juegos"));
            	        System.out.println("Ingresos por Comida: $" + datos.get("comida"));
            	        System.out.println("Impuestos (IVA): $" + datos.get("impuestos"));
            	        System.out.println("Propinas: $" + datos.get("propinas"));
            	        System.out.println("TOTAL NETO: $" + datos.get("totalNeto"));
            	    } catch (Exception e) {
            	        System.out.println("Error: Formato de fecha inválido o datos insuficientes.");
            	    }
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