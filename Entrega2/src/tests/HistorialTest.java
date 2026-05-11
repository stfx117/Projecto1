package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import administration.Historial;
import sales.Venta;
import services.Mesa;
import services.Prestamo;
import users.Cliente;

public class HistorialTest {
	@Test
	public void testCrearHistorial() {
		Mesa mesa = new Mesa(1, 4, false);
		Cliente cliente = new Cliente("CLIENTE", 1, "Andres", "asdhjk@asd.com", "sadhjlkas", "hdsajkad", "01/01/2000", 0, "231asd");
		Venta v = new Venta(1, "01/01/2330", false);
		Prestamo p = new Prestamo(1, mesa, false, cliente);
		Historial h = new Historial();
		h.agregarPrestamo(p);
		h.agregarVenta(v);
		assertEquals(p, h.getPrestamo(p.getId()));
		assertEquals(v, h.getVenta(v.getId()));
		assertEquals(true, h.comprobarVenta(v.getId()));
		assertEquals(true, h.comprobarPrestamo(p.getId()));
		assertEquals(v, h.getVenta(v.getId()));
		assertEquals(p, h.getPrestamo(p.getId()));
		assertEquals(null, h.getVenta(0));
		assertEquals(null, h.getPrestamo(0));
		h.eliminarPrestamo(p.getId());
		h.eliminarVenta(v.getId());
		assertEquals(false, h.comprobarVenta(v.getId()));
		assertEquals(false, h.comprobarPrestamo(p.getId()));
		
	}

}
