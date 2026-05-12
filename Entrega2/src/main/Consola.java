package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
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
import interfas.GestorInventario;
import interfas.GestorHistorial;
import interfas.GestorSugerencias;
import interfas.GestorTurno;
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
    private GestorTurno gestorT;
    
    public Consola()
    {
    	this.cafe = BoardGameCafe.getInstancia("BoardGameCafe");
    	this.gestorU = new GestorUsuario();
    	this.gestorA = new GestorArchivo();
    	this.gestorI = new GestorInventario();
    	this.gestorH = new GestorHistorial(gestorI);
    	this.gestorS = new GestorSugerencias(gestorU);
    	this.gestorT = new GestorTurno(gestorU);
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
		int opcion = 0;
		
		do {
			System.out.println("\n--- PANEL DE MESERO ---");
	        System.out.println("1. Planear turno");
	        System.out.println("2. Realizar sugerencia");
	        System.out.println("3. Agregar un juego a la lista");
	        System.out.println("4. Comprar un juego con codigo de descuento");
	        System.out.println("5. Cerrar Sesión");
	        System.out.print("Seleccione una opción: ");
	        
	        try {
            	opcion = Integer.parseInt(sc.nextLine());
            	switch (opcion) {
            	case 1:
            		System.out.println("\n--- PLANIFICAR MI TURNO ---");
            	    Turno nuevoTurno = new Turno(u); 
            	    String continuar = "s";
            	    
            	    while(continuar.equalsIgnoreCase("s")) {
            	        System.out.print("Día (LUNES, MARTES, etc.): ");
            	        String dia = sc.nextLine().toUpperCase();
            	        System.out.print("Rango de horas (ej: 08:00-12:00): ");
            	        String rango = sc.nextLine();
            	        
            	        nuevoTurno.getDIASEMANAHORA().putIfAbsent(dia, new ArrayList<>());
            	        nuevoTurno.getDIASEMANAHORA().get(dia).add(rango);
            	        
            	        System.out.print("¿Agregar otro día? (s/n): ");
            	        continuar = sc.nextLine();
            	    }
            	    
            	    gestorT.guardarTurno(nuevoTurno); 
            	    System.out.println("Turno guardado correctamente.");
            		break;
            	case 2:
            		System.out.println("\n--- ENVIAR SUGERENCIA AL ADMIN ---");
            	    System.out.print("Escriba su mensaje: ");
            	    String msj = sc.nextLine();
            	    
            	    int idSugerencia = gestorS.generarNuevoId();
            	    Sugerencia nuevaS = new Sugerencia(idSugerencia, u, msj);
            	    
            	    gestorS.guardarNuevaSugerencia(nuevaS);
            	    System.out.println("Sugerencia enviada con éxito.");
            		break;
            	case 3:
            		System.out.print("Ingrese el ID del juego que ya domina: ");
            	    int idJuego = Integer.parseInt(sc.nextLine());
            	    
            	    products.Producto p = gestorI.buscarProductoPorId(idJuego);
            	    
            	    if (p instanceof products.JuegoDeMesa) {
            	        u.agregarJuegoDominado((products.JuegoDeMesa) p);

            	        gestorU.actualizarArchivo(); 
            	        System.out.println("¡Juego agregado a tu lista de maestría!");
            	    } else {
            	        System.out.println("ID no encontrado o no es un juego de mesa.");
            	    }
                    break;
            	case 4:
            		System.out.println("\n--- COMPRA CON DESCUENTO DE EMPLEADO ---");
            	    System.out.print("Ingrese su código de descuento: ");
            	    String codIngresado = sc.nextLine();
            	    
            	    if (!codIngresado.equalsIgnoreCase("NONE") && !codIngresado.equalsIgnoreCase("ninguno")) {
            	        System.out.println("¡Código válido aplicado!");
            	        
            	        System.out.print("ID del juego a comprar: ");
            	        int idC = Integer.parseInt(sc.nextLine());
            	        products.Producto prod = gestorI.buscarProductoPorId(idC);
            	        
            	        if (prod != null) {
            	            double precioFinal = prod.getPrecioBase() * 0.80; 
            	            System.out.println("Precio original: " + prod.getPrecioBase());
            	            System.out.println("Precio con descuento: " + precioFinal);
            	            
            	            System.out.println("Compra procesada con éxito.");
            	        }
            	    } else {
            	        System.out.println("Código inválido. No se puede proceder con esta opción.");
            	    }
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
            		System.out.println("\n--- SUGERENCIAS PENDIENTES ---");
            	    List<Sugerencia> lista = gestorS.getSugerencias();
            	    
            	    if (lista == null || lista.isEmpty()) {
            	    	System.out.println("No hay ninguna sugerencia registrada en el sistema.");
            	    } else {
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