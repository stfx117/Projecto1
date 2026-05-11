package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import inventory.Inventario;
import products.Bebida;
import products.JuegoDeMesa;
import products.Pasteleria;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;


public class InventarioTest {
	@Test
	public void testCrearInventario() {
		Inventario i = new Inventario();
		Pasteleria p1 = new Pasteleria(1, "pansito", 1000, "Delicioso pan");
		Bebida b1 = new Bebida(1, "Limonada", 6000, "Awita con limon", false, false);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 10000, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 60000, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		i.agregarJuegoPrestamo(j2);
		i.agregarJuegoVenta(j1);
		i.agregarProductoIngerible(p1);
		i.agregarProductoIngerible(b1);
		assertEquals(p1, i.getProductosIngeribles(0));
		assertEquals(j2, i.getJuegosPrestamo(0));
		assertEquals(j1, i.getJuegosVenta(0));
		i.eliminarProductoIngerible(0);
		i.eliminarProductoJuegoPrestamo(0);
		i.eliminarProductoJuegoVenta(0);
		assertEquals(b1, i.getProductosIngeribles(0));
		assertEquals(null, i.getJuegosPrestamo(0));
		assertEquals(null, i.getJuegosVenta(0));
		i.agregarProductoIngerible(p1);
		i.agregarJuegoPrestamo(j2);
		i.agregarJuegoVenta(j1);
		i.vaciarJuegosPrestamo();
		i.vaciarJuegosVenta();
		i.vaciarProductosIngeribles();
		assertEquals(null, i.getJuegosPrestamo(0));
		assertEquals(null, i.getJuegosVenta(0));
		assertEquals(null, i.getProductosIngeribles(0));
		
		
	}

}
