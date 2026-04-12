package services;

import users.Cliente;
import services.Mesa;

public class Reserva
{
	//Atributos
	private int id;
	private Cliente cliente;
	private String mesa;
	private int personas;
	private boolean menores5Anios;
	private boolean hayJovenes;
	private String estadia;
	
	//Constructor
	public Reserva(int id, Cliente cliente, String mesa, int personas, boolean menores5Anios, boolean hayJovenes, String estadia)
	{
		this.setId(id);
		this.setCliente(cliente);
		this.setMesa(mesa);
		this.setPersonas(personas);
		this.setMenores5Anios(menores5Anios);
		this.setHayJovenes(hayJovenes);
		this.setEstadia(estadia);
	}

	//Metodos
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getMesa() {
		return mesa;
	}

	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public int getPersonas() {
		return personas;
	}

	public void setPersonas(int personas) {
		this.personas = personas;
	}

	public boolean isMenores5Anios() {
		return menores5Anios;
	}

	public void setMenores5Anios(boolean menores5Anios) {
		this.menores5Anios = menores5Anios;
	}

	public boolean isHayJovenes() {
		return hayJovenes;
	}

	public void setHayJovenes(boolean hayJovenes) {
		this.hayJovenes = hayJovenes;
	}

	public String getEstadia() {
		return estadia;
	}

	public void setEstadia(String estadia) {
		this.estadia = estadia;
	}
	
	public boolean validarRestriccionDeEdad(JuegoDeMesa juego)
	{
		return false;
	}
	
	public boolean capasidadMesa()
	{
		if(this.personas >= this.mesa.getCapasidadMaxima())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
}