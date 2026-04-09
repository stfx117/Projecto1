package products;

public abstract class Producto {
	private int id;
	private String nombre;
	private double precioBase;
	private String descripcion;
	
	public Producto(int id, String nombre, double precioBase, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.descripcion = descripcion;
	}
	
	public abstract double calcularPrecioFinal();
	
}
