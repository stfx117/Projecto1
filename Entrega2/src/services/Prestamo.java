package services;

import java.util.ArrayList;
import java.util.List;

import interfas.GuardadoTxt;
import products.JuegoDeMesa;
import users.Cliente;
import users.Mesero;

public class Prestamo implements GuardadoTxt{
	private int id;
	private ArrayList<JuegoDeMesa> juegos;
	private Mesa mesaAsociada;
	private boolean advertenciaSinMesero;
	private Cliente clienteAsociado;
	
	public Prestamo(int id, Mesa mesaAsociada, boolean advertenciaSinMesero, Cliente clienteAsociado) {
		this.id = id;
		this.juegos = new ArrayList<JuegoDeMesa>();
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
		List<JuegoDeMesa> listaJuegos = mesero.getJUEGOSDOMINADOS();
		for (int i = 0; i < listaJuegos.size(); i++) {
			JuegoDeMesa juegoMesero = listaJuegos.get(i);
			for (int j = 0; j < this.juegos.size(); j++) {
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
	
	public JuegoDeMesa getJuegos(int id) {
		if (juegos.size() > 0){
			if (juegos.size() > id) {
				return this.juegos.get(id);
				
				
		}
			return null;
		}
		return null;
		
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

	@Override
	public String toLineaTxt() {
		StringBuilder prestamo = new StringBuilder();
		
		prestamo.append(this.id).append(",");
		prestamo.append(this.mesaAsociada.getId()).append(",");
		prestamo.append(this.advertenciaSinMesero).append(",");
		prestamo.append(this.clienteAsociado.getId()).append(",");
		
		if(this.juegos != null && !this.juegos.isEmpty())
		{
			List<String> idJuegos = new ArrayList<>();
			
			for(JuegoDeMesa j : this.juegos)
			{
				idJuegos.add(String.valueOf(j.getId()));
			}
			prestamo.append(String.join("-", idJuegos));
			
		} else
		{
			prestamo.append("NONE");
		}
		
		return prestamo.toString();
	}
}