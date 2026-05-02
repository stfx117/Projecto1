package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import products.Bebida;
import products.JuegoDeMesa;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;
import products.Pasteleria;
import sales.Venta;

public class VentaTest {

	@Test
	public void testCrearVenta() {
		Venta v = new Venta(1, "01/01/2330", false);
		assertEquals(1, v.getId());
		assertEquals("01/01/2330", v.getFecha());
		assertEquals(false, v.getPropina());
	}

	@Test
	public void testAgregarProducto() {
		Venta v = new Venta(1, "01/01/2330", false);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		v.agregarProducto(j1);
		v.agregarProducto(j2);
		assertEquals(j1, v.getItems(0));
	}
	
	@Test
	public void testEliminarProducto() {
		Venta v = new Venta(1, "01/01/2330", false);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 1.22, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		v.agregarProducto(j1);
		v.agregarProducto(j2);
		v.eliminarProducto(0);
		assertEquals(null, v.getItems(1));
		v.eliminarProducto(0);
		assertEquals(null, v.getItems(0));
	}
	
	@Test
	public void testAplicarPropinaDada() {
		Venta v = new Venta(1, "01/01/2330", false);
		JuegoDeMesa j1 = new JuegoDeMesa(1, "Uno", 10000, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		JuegoDeMesa j2 = new JuegoDeMesa(2, "Exploding Kittens", 60000, "juego muy lindo", 1099, "hasbro", 1, 8, restriccionEdad.ADULTOS, false, EstadoFisico.OPTIMO, Categoria.CARTAS, false);
		v.agregarProducto(j1);
		v.agregarProducto(j2);
		v.calcularPrecio();
		v.aplicarPropina(0.9);
		assertEquals(158270, v.getSubtotal());
	}
	
	@Test
	public void testAplicarPropinaStandard() {
		Venta v = new Venta(1, "01/01/2330", false);
		Pasteleria p1 = new Pasteleria(1, "pansito", 1000, "Delicioso pan");
		Bebida b1 = new Bebida(1, "Limonada", 6000, "Awita con limon", false, false);
		p1.agregarAlergeno("gluten");
		assertEquals("gluten", p1.getAlergenos(0));
		p1.agregarAlergeno("huevo");
		p1.eliminarAlergenos(0);
		assertEquals("huevo", p1.getAlergenos(0));
		p1.clearAlergenos();
		assertEquals(null, p1.getAlergenos(0));
		v.agregarProducto(p1);
		v.agregarProducto(b1);
		v.calcularPrecio();
		v.aplicarPropina();
		assertEquals(8316, v.getSubtotal());
	}
}
