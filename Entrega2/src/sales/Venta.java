package sales;

import java.util.List;

import products.Producto;

public class Venta{
	private int id;
	private String fecha;
	private List<Producto> items;
	private boolean propina;
	private double subtotal;
	private double total;
	
	public Venta(int id, String fecha, List<Producto> items, boolean propina) {
		this.id = id;
		this.fecha = fecha;
		this.items = items;
		this.setPropina(propina);
		this.subtotal = 0;
		this.total = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Producto> getItems() {
		return items;
	}

	public void setItems(List<Producto> items) {
		this.items = items;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public boolean getPropina() {
		return propina;
	}

	public void setPropina(boolean propina) {
		this.propina = propina;
	}
	
	public void agregarProducto(Producto producto) {
		this.items.add(producto);
	}
	
	public void eliminarProducto(int idEnItems) {
		this.items.remove(idEnItems);
	}
	
	public void calcularPrecio() {
		this.subtotal = 0;
		
		for (int i = 0; i < items.size(); i++) {
			Producto auxProducto = items.get(i);
			double auxPrecioFinal = auxProducto.calcularPrecioFinal();
			this.subtotal = this.subtotal + auxPrecioFinal;
		}
	}
	
	public void aplicarPropina() {
		this.total = this.subtotal + (this.subtotal * 0.1);
		setPropina(true);
	}
	
	public void aplicarPropina(double propina) {
		this.total = this.subtotal + this.subtotal * propina;
		setPropina(true);
	}
}