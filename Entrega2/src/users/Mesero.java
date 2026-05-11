package users;

import java.util.ArrayList;
import java.util.List;
import products.JuegoDeMesa;

public class Mesero extends Empleado
{
	//Atributos
	private List<JuegoDeMesa> JUEGOSDOMINADOS = new ArrayList<>();
	
	//Cnstructor
	public Mesero(String rol, int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(rol, id, nombre, email, login, password, codigoDesceunto, estaEnTurno);
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
	
	@Override
	public String toLineaTxt() 
	{
		
		return String.format("rol, id, nombre, email, login, password, codigoDesceunto, estaEnTurno, JUEGOSDOMINADOS",
				rol,
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