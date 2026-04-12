package users;

import java.util.ArrayList;
import java.util.List;
import administration.Caja;
import products.JuegoDeMesa;

public class Mesero extends Empleado
{
	//Atributos
	private List<JuegoDeMesa> JUEGOSDOMINADOS = new ArrayList<>();
	
	//Cnstructor
	public Mesero(int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(id, nombre, email, login, password, codigoDesceunto, estaEnTurno);
	}

	//Metodos
	public List<JuegoDeMesa> getJUEGOSDOMINADOS() 
	{
		return JUEGOSDOMINADOS;
	}

	public void setJUEGOSDOMINADOS(List<JuegoDeMesa> JUEGOSDOMINADOS) 
	{
		this.JUEGOSDOMINADOS = JUEGOSDOMINADOS;
	}
	
	public void agregarJuegoDominado(JuegoDeMesa juego) 
	{
        this.JUEGOSDOMINADOS.add(juego);
    }
	
	public boolean dominaJuego(JuegoDeMesa juego) 
	{
        return this.JUEGOSDOMINADOS.contains(juego);
    }
	
	public double operacionesCaja(Caja caja, double totalAPagar, double dineroEntregado)
	{
	      if (dineroEntregado < totalAPagar)
	       {
	    	  throw new IllegalArgumentException("Dinero insuficiente. Faltan " + (totalAPagar - dineroEntregado));
	       }
	        
	       caja.recibirDinero(dineroEntregado);
	        
	       double cambio = dineroEntregado - totalAPagar;
	        
	       if (cambio > 0) 
	       {
	    	   caja.darDinero(cambio);
	       }
	        
	       return cambio;
	}
	
	@Override
	public String toLineaTxt() 
	{
		
		return String.format("id, nombre, email, login, password, codigoDesceunto, estaEnTurno, JUEGOSDOMINADOS",
				id,
				nombre,
				email,
				login,
				password,
				codigoDescuento,
				estaEnTurno,
				JUEGOSDOMINADOS);
	}	
}