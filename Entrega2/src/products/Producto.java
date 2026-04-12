package products;

public abstract class Producto {
	protected int id;
	private String nombre;
	private double precioBase;
	private String descripcion;
	
	public Producto(int id, String nombre, double precioBase, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.descripcion = descripcion;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double getPrecioBase() {
		return this.precioBase;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public abstract double calcularPrecioFinal();
	
}
