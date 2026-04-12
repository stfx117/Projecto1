package products;

public class JuegoDeMesa extends Producto {
	private int anioPublicacion;
	private String empresa;
	private int minJugadores;
	private int maxJugadores;
	private String restriccionEdad;
	private boolean esDificil;
	public enum EstadoFisico {DESAPARECIDO, DAÑADO, OPTIMO};
	public enum Categoria {CARTAS, TABLERO, ACCION}
	private boolean paraVenta;
	private EstadoFisico estadoFisico;
	private Categoria categoria;
	
	
	
	public JuegoDeMesa(int id, String nombre, double precioBase, String descripcion, int anioPublicacion, String empresa, int minJugadores, int maxJugadores, String restriccionEdad, boolean esDificil, EstadoFisico estadoFisico, Categoria categoria, boolean paraVenta) {
		super(id, nombre, precioBase, descripcion);
		this.anioPublicacion = anioPublicacion;
		this.empresa = empresa;
		this.minJugadores = minJugadores;
		this.maxJugadores = maxJugadores;
		this.restriccionEdad = restriccionEdad;
		this.esDificil = esDificil;
		this.paraVenta = paraVenta;
		this.estadoFisico = estadoFisico;
		this.categoria = categoria;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public int getMinJugadores() {
		return minJugadores;
	}

	public void setMinJugadores(int minJugadores) {
		this.minJugadores = minJugadores;
	}

	public int getMaxJugadores() {
		return maxJugadores;
	}

	public void setMaxJugadores(int maxJugadores) {
		this.maxJugadores = maxJugadores;
	}

	public boolean getEsDificil() {
		return esDificil;
	}

	public void setEsDificil(boolean esDificil) {
		this.esDificil = esDificil;
	}
	
	public String getRestriccionEdad() {
		return restriccionEdad;
	}

	public void setRestriccionEdad(String restriccionEdad) {
		this.restriccionEdad = restriccionEdad;
	}

	public boolean isParaVenta() {
		return paraVenta;
	}

	public void setParaVenta(boolean paraVenta) {
		this.paraVenta = paraVenta;
	}

	public EstadoFisico getEstadoFisico() {
		return estadoFisico;
	}

	public void setEstadoFisico(EstadoFisico estadoFisico) {
		this.estadoFisico = estadoFisico;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public double calcularPrecioFinal() {
		double precioFinal = (getPrecioBase()*(19/100)) + getPrecioBase();
		return precioFinal;
	}

}
