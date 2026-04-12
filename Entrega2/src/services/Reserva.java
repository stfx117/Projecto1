package services;

import users.Cliente;
import products.JuegoDeMesa;
import services.Mesa;

public class Reserva
{
	//Atributos
	private int id;
	private Cliente cliente;
	private Mesa mesa;
	private int personas;
	private boolean menores5Anios;
	private boolean hayJovenes;
	private String estadia;
	
	//Constructor
	public Reserva(int id, Cliente cliente, Mesa mesa, int personas, boolean menores5Anios, boolean hayJovenes, String estadia)
	{
		this.id = id;
		this.cliente = cliente;
		this.mesa = mesa;
		this.personas = personas;
		this.menores5Anios = menores5Anios;
		this.hayJovenes = hayJovenes;
		this.estadia = estadia;
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

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
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
		if(this.personas >= this.mesa.getCapacidadMaxima())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
}