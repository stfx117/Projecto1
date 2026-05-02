package tests;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import services.Prestamo;
import products.JuegoDeMesa;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;
import users.Cliente;
import users.Mesero;
import services.Mesa;

public class PrestamoTest {
	@Test
	public void testCrearPrestamo() {

	    Cliente cliente = new Cliente(1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
	    Mesa mesa = new Mesa(1, 4, false);

	    Prestamo p = new Prestamo(1, mesa, false, cliente);

	    assertEquals(1, p.getId());
	    assertEquals(cliente, p.getClienteAsociado());
	    assertEquals(mesa, p.getMesa());
	}
	
	@Test
	public void testAñadirJuego() {
		Mesa mesa = new Mesa(1, 4, false);
		Cliente cliente = new Cliente(1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
		Prestamo p = new Prestamo(1, mesa, false, cliente);
		JuegoDeMesa j = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		p.agregarJuego(j);
		assertEquals(j, p.getJuegos(0));
	}
	
	@Test
	public void testLimpiarListaJuegos() {
		Mesa mesa = new Mesa(1, 4, false);
		Cliente cliente = new Cliente(1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
		Prestamo p = new Prestamo(1, mesa, false, cliente);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		p.agregarJuego(j1);
		p.agregarJuego(j2);
		p.limpiarListaJuegos();
		assertEquals(null, p.getJuegos(0));
		
	}
	
	@Test
	public void testEliminarJuego() {
		Mesa mesa = new Mesa(1, 4, false);
		Cliente cliente = new Cliente(1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
		Prestamo p = new Prestamo(1, mesa, false, cliente);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		p.agregarJuego(j1);
		p.agregarJuego(j2);
		p.eliminarJuego(0);
		assertEquals(null, p.getJuegos(1));
		
	}
	
	public void testMaestriaMesero() {
		Mesa mesa = new Mesa(1, 4, false);
		Cliente cliente = new Cliente(1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
		Prestamo p = new Prestamo(1, mesa, false, cliente);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		Mesero m = new Mesero(1, "Pepinoto", "peroi@jaslkd.com", "asdjlksd", "kasjd", "231asdasd", true);
		p.agregarJuego(j1);
		p.agregarJuego(j2);
		p.verificarMaestriaMesero(m);
	}
}