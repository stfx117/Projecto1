package services;

import java.util.List;

import products.JuegoDeMesa;
import users.Cliente;
import users.Mesero;

public class Prestamo{
	private int id;
	private List<JuegoDeMesa> juegos;
	private Mesa mesaAsociada;
	private boolean advertenciaSinMesero;
	private Cliente clienteAsociado;
	
	public Prestamo(int id, List<JuegoDeMesa> juegos, Mesa mesaAsociada, boolean advertenciaSinMesero, Cliente clienteAsociado) {
		this.id = id;
		this.juegos = juegos;
		this.mesaAsociada = mesaAsociada;
		this.advertenciaSinMesero = advertenciaSinMesero;
		this.clienteAsociado = clienteAsociado;
	}
	
	public void agregarJuego(JuegoDeMesa juego) {
		this.juegos.add(juego);
	}
	
	public void eliminarJuego(int id) {
		this.juegos.remove(id);
	}
	
	public void limpiarListaJuegos() {
		this.juegos.clear();
	}
	
	public boolean verificarMaestriaMesero(Mesero mesero) {
		List<JuegoDeMesa> listaJuegos = mesero.getJuegosDominados();
		for (int i = 0; i < listaJuegos.size(); i++) {
			JuegoDeMesa juegoMesero = listaJuegos.get(i);
			for (int j = 0; i < this.juegos.size(); j++) {
				JuegoDeMesa juegoPrestamo = this.juegos.get(j);
				if (juegoMesero.getId() == juegoPrestamo.getId()) {
					return true;
				}
				
			}
		}
		
		return false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<JuegoDeMesa> getJuegos() {
		return this.juegos;
	}
	
	public Mesa getMesa() {
		return this.mesaAsociada;
	}
	
	public void setMesa(Mesa mesa) {
		this.mesaAsociada = mesa;
	}
	
	public boolean getAdvertenciaSinMesero() {
		return this.advertenciaSinMesero;
	}
	
	public void setAdvertenciaSinMesero(boolean x) {
		this.advertenciaSinMesero = x;
	}
	
	public Cliente getClienteAsociado() {
		return this.clienteAsociado;
	}
	
	public void setClienteAsociado(Cliente cliente) {
		this.clienteAsociado = cliente;
	}
}