package products;

import java.util.ArrayList;

public class Pasteleria extends ProductoIngerible{
	private ArrayList<String> alergenos;
	
	public Pasteleria(int id, String nombre, double precioBase, String descripcion){
		super(id, nombre, precioBase, descripcion);
		this.alergenos = new ArrayList<String>();
	}

	public String getAlergenos(int index) {
		return alergenos.get(index);
	}

	public void eliminarAlergenos(int index) {
		this.alergenos.remove(index);
	}
	
	public void clearAlergenos() {
		alergenos.clear();
	}

	@Override
	public double calcularPrecioFinal() {
		double precioFinal = getPrecioBase()*(8/100);
		return precioFinal;
	}

	@Override
	public String toLineaTxt() {
		StringBuilder pasteleria = new StringBuilder();
		pasteleria.append(this.id).append(",");
		pasteleria.append(this.nombre).append(",");
		pasteleria.append(String.valueOf(this.precioBase)).append(",");
		pasteleria.append(this.descripcion).append(",");
		
		if(this.alergenos != null && !this.alergenos.isEmpty())
		{
			pasteleria.append(String.join("-", this.alergenos));
		}
		
		return pasteleria.toString();
	}

}
