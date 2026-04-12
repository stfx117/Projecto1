package administration;

import java.util.Map;
import java.util.HashMap;

import sales.Venta;
import services.Prestamo;

public class Historial{
	private Map<Integer, Venta> historialVentas;
	private Map<Integer, Prestamo> historialPrestamos;
	
	public Historial() {
		this.historialVentas = new HashMap<>();
		this.historialPrestamos = new HashMap<>();
	}
	
	public void agregarVenta(Venta venta) {
		this.historialVentas.put(venta.getId(), venta);
	}
	
	public void agregarPrestamo(Prestamo prestamo) {
		this.historialPrestamos.put(prestamo.getId(), prestamo);
	}
	
	public Venta getVenta(int id) {
		return this.historialVentas.get(id);
	}
	
	public Prestamo getPrestamo(int id) {
		return this.historialPrestamos.get(id);
	}
	
	public boolean comprobarVenta(int id) {
		return this.historialVentas.containsKey(id);
	}
	
	public boolean comprobarPrestamo(int id) {
		return this.historialPrestamos.containsKey(id);
	}
	
	public void eliminarVenta(int id) {
		this.historialVentas.remove(id);
	}
	
	public void eliminarPrestamo(int id) {
		this.historialPrestamos.remove(id);
	}
}