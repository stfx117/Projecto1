package products;

import java.util.List;

public class Pasteleria extends Producto{
	private List<String> alergenos;
	
	public Pasteleria(int id, String nombre, double precioBase, String descripcion, List<String> alergenos){
		super(id, nombre, precioBase, descripcion);
		this.alergenos = alergenos;
	}

	public List<String> getAlergenos() {
		return alergenos;
	}

	public void setAlergenos(List<String> alergenos) {
		this.alergenos = alergenos;
	}

}
