package products;

public class Bebida extends Producto{
	private boolean esAlcoholica;
	private boolean esCaliente;
	
	public Bebida(int id, String nombre, double precioBase, String descripcion, boolean esAlcoholica, boolean esCaliente) {
		super(id, nombre, precioBase, descripcion);
		this.esAlcoholica = esAlcoholica;
		this.esCaliente = esCaliente; 
	}

	public boolean getEsAlcoholica() {
		return esAlcoholica;
	}

	public void setEsAlcoholica(boolean esAlcoholica) {
		this.esAlcoholica = esAlcoholica;
	}

	public boolean getEsCaliente() {
		return esCaliente;
	}

	public void setEsCaliente(boolean esCaliente) {
		this.esCaliente = esCaliente;
	}
	
	
	
	
}