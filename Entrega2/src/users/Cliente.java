package users;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario
{
	//Atributos
	private String fechaNacimiento;
	private int puntosFidelidad;
	private String codigoDescuento;
	private List<String> juegosFavoritos = new ArrayList<>();
	
	//Constructor
	public Cliente(int id, String nombre, String email, String login, String password, String fechaNacimiento, int puntosFidelidad, String codigoDescuento)
	{
		super(id, nombre, email, login, password);
		this.setFechaNacimiento(fechaNacimiento);
		this.setPuntosFidelidad(puntosFidelidad);
		this.setCodigoDescuento(codigoDescuento);
	}

	//Metodos
	public String getFechaNacimiento() 
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) 
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getPuntosFidelidad() {
		return puntosFidelidad;
	}

	public void setPuntosFidelidad(int puntosFidelidad) 
	{
		this.puntosFidelidad = puntosFidelidad;
	}

	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public void setCodigoDescuento(String codigoDescuento) 
	{
		this.codigoDescuento = codigoDescuento;
	}

	public List<String> getJuegosFavoritos() 
	{
		return juegosFavoritos;
	}
	
	public void actualizarPuntos(int puntosNuevos)
	{
		this.puntosFidelidad += puntosNuevos;
	}
	
	public void anadirJuego(String juego)
	{
		this.juegosFavoritos.add(juego);
	}

	@Override
	public String toLineaTxt() 
	{
		return String.format("id, nombre, email, login, password, fechaNacimiento, puntosFidelidad, codigoDescuento",
				id,
				nombre,
				email,
				login,
				password,
				fechaNacimiento,
				puntosFidelidad,
				codigoDescuento);
	}
	
	
}
