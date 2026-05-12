package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import administration.Sugerencia;
import administration.Turno;
import cafe.BoardGameCafe;
import interfas.GestorUsuario;
import products.JuegoDeMesa;
import products.Producto;
import sales.Venta;
import services.Mesa;
import services.Prestamo;
import services.Reserva;
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
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU COCINERO ---");
            System.out.println("1. Sugerir turno");
            System.out.println("2. Sugerir producto");
            System.out.println("3. Realizar compra");
            System.out.println("0. Cerrar sesión");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch(opcion) {
                    case 1:
                        sugerirTurno(u);
                        break;
                    case 2:
                        sugerirProducto(u);
                        break;
                    case 3:
                        realizarVentaCocinero(u);
                        break;
                    case 0:
                        System.out.println("Sesión cerrada.");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch(Exception e) {
                System.out.println("Error en entrada.");
            }
        }
    }
    
    private void sugerirTurno(Cocinero cocinero) {
        System.out.println("\n--- SUGERIR TURNO ---");
        Turno turno = new Turno(cocinero);
        System.out.print("Ingrese día: ");
        String dia = sc.nextLine();
        System.out.print("Ingrese hora inicio: ");
        String inicio = sc.nextLine();
        System.out.print("Ingrese hora fin: ");
        String fin = sc.nextLine();
        List<String> horas = new ArrayList<>();
        horas.add(inicio);
        horas.add(fin);
        turno.getDIASEMANAHORA().put(dia, horas);
        System.out.println("Turno sugerido correctamente.");
        System.out.println(turno.toLineaTxt());
    }
    
    private void sugerirProducto(Cocinero cocinero) {
        System.out.println("\n--- SUGERIR PRODUCTO ---");
        System.out.print("Ingrese ID sugerencia: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese mensaje de sugerencia: ");
        String mensaje = sc.nextLine();
        Sugerencia sugerencia = new Sugerencia(id, cocinero, mensaje);
        System.out.println("Sugerencia enviada.");
        System.out.println(sugerencia.toLineaTxt());
    }

    private void realizarVentaCocinero(Cocinero cocinero) {
        System.out.println("\n--- REALIZAR VENTA ---");
        System.out.print("Ingrese ID venta: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese fecha: ");
        String fecha = sc.nextLine();
        Venta venta = new Venta(id, fecha, false);
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n1. Agregar producto");
            System.out.println("2. Aplicar propina");
            System.out.println("3. Calcular subtotal");
            System.out.println("0. Finalizar venta");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion) {
                case 1:
                    Producto producto = null;
                    venta.agregarProducto(producto);
                    System.out.println("Producto agregado.");
                    break;
                case 2:
                    venta.aplicarPropina();
                    System.out.println("Propina aplicada.");
                    break;
                case 3:
                    venta.calcularPrecio();
                    System.out.println("Subtotal actual: " + venta.getSubtotal());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        System.out.println("Venta finalizada.");
        System.out.println(venta.toLineaTxt());
    }
    
    
    private void mostrarCliente(Cliente u) {
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Realizar reserva");
            System.out.println("2. Solicitar préstamo");
            System.out.println("3. Realizar compra");
            System.out.println("0. Cerrar sesión");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch(opcion) {
                    case 1:
                        realizarReserva(u);
                        break;
                    case 2:
                        realizarPrestamo(u);
                        break;
                    case 3:
                        realizarVenta(u);
                        break;
                    case 0:
                        System.out.println("Sesión cerrada.");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch(Exception e) {
                System.out.println("Error en entrada.");
            }
        }
    }
    
    private void realizarReserva(Cliente cliente) {
        System.out.println("\n--- REALIZAR RESERVA ---");
        System.out.print("Ingrese ID reserva: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Cantidad de personas: ");
        int personas = Integer.parseInt(sc.nextLine());
        System.out.print("¿Hay menores de 5 años? (true/false): ");
        boolean menores = Boolean.parseBoolean(sc.nextLine());
        System.out.print("¿Hay jóvenes? (true/false): ");
        boolean jovenes = Boolean.parseBoolean(sc.nextLine());
        System.out.print("Tiempo estimado de estadía: ");
        String estadia = sc.nextLine();
        Mesa mesa = null;
        Reserva reserva = new Reserva(id, cliente, mesa, personas, menores, jovenes, estadia);
        System.out.println("Reserva creada correctamente.");
        System.out.println(reserva.toLineaTxt());
    }
    
    private void realizarPrestamo(Cliente cliente) {
        System.out.println("\n--- REALIZAR PRESTAMO ---");
        System.out.print("Ingrese ID préstamo: ");
        int id = Integer.parseInt(sc.nextLine());
        Mesa mesa = null;
        Prestamo prestamo = new Prestamo(id, mesa, false, cliente);
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n1. Agregar juego");
            System.out.println("0. Finalizar préstamo");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion) {
                case 1:
                    JuegoDeMesa juego = null;
                    prestamo.agregarJuego(juego);
                    System.out.println("Juego agregado al préstamo.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        System.out.println("Préstamo realizado.");
        System.out.println(prestamo.toLineaTxt());
    }
    
    private void realizarVenta(Cliente cliente) {
        System.out.println("\n--- REALIZAR VENTA ---");
        System.out.print("Ingrese ID venta: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Ingrese fecha: ");
        String fecha = sc.nextLine();
        Venta venta = new Venta(id, fecha, false);
        int opcion = -1;
        while(opcion != 0) {
            System.out.println("\n1. Agregar producto");
            System.out.println("2. Aplicar propina");
            System.out.println("3. Calcular subtotal");
            System.out.println("0. Finalizar venta");
            opcion = Integer.parseInt(sc.nextLine());
            switch(opcion) {
                case 1:
                    Producto producto = null;
                    venta.agregarProducto(producto);
                    System.out.println("Producto agregado.");
                    break;
                case 2:
                    venta.aplicarPropina();
                    System.out.println("Propina aplicada.");
                    break;
                case 3:
                    venta.calcularPrecio();
                    System.out.println("Subtotal actual: " + venta.getSubtotal());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        System.out.println("Venta finalizada.");
        System.out.println(venta.toLineaTxt());
    }

	private void mostrarMesero(Mesero u) {
		// TODO Auto-generated method stub
		
	}

	private void mostrarAdmin(Administrador u) {
		// TODO Auto-generated method stub
		
	}
}