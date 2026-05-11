package sales;

import java.util.ArrayList;
import java.util.List;

import interfas.GuardadoTxt;
import products.Producto;

public class Venta implements GuardadoTxt{
	private int id;
	private String fecha;
	private ArrayList<Producto> items;
	private boolean propina;
	private double subtotal;
	
	public Venta(int id, String fecha, boolean propina) {
		this.id = id;
		this.fecha = fecha;
		this.items = new ArrayList<Producto>();
		this.propina = propina;
		this.subtotal = 0;
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

	public Producto getItems(int index) {
		if (items.size() > 0){
			if (items.size() > index) {
				return this.items.get(index);
			}
			return null;
		}
		return null;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
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
			auxProducto = null;
			
		}
	}
	
	public void aplicarPropina() {
		this.subtotal = this.subtotal + (this.subtotal * 0.1);
		setPropina(true);
	}
	
	public void aplicarPropina(double propina) {
		this.subtotal = this.subtotal + this.subtotal * propina;
		setPropina(true);
	}

	@Override
	public String toLineaTxt() {
		StringBuilder venta = new StringBuilder();
		
		venta.append(this.id).append(",");
		venta.append(this.fecha).append(",");
		venta.append(this.propina).append(",");
		venta.append(this.subtotal).append(",");
		venta.append(this.subtotal).append(",");
		
		if(this.items != null && !this.items.isEmpty())
		{
			List<String> idProductos = new ArrayList<>();
			
			for(Producto p: this.items)
			{
				idProductos.add(String.valueOf(p.getId()));
			}
			
			venta.append(String.join("-", idProductos));
		} else
		{
			venta.append("sin Prodcutos");
		}
		
		return venta.toString();
	}
	
	public ArrayList<Producto> getItems() {
	    if (this.items == null) {
	        return new ArrayList<>();
	    }
	    return this.items;
	}
}