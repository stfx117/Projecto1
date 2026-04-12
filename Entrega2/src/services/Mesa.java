package services;

public class Mesa{
	private int id;
	private int capacidadMaxima;
	private boolean estaOcupada;
	
	public Mesa(int id, int capacidadMaxima, boolean estaOcupada) {
		this.setId(id);
		this.setCapacidadMaxima(capacidadMaxima);
		this.setEstaOcupada(estaOcupada);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}

	public boolean isEstaOcupada() {
		return estaOcupada;
	}

	public void setEstaOcupada(boolean estaOcupada) {
		this.estaOcupada = estaOcupada;
	}
	
	
}