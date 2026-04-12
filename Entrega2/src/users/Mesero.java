package users;

import java.util.ArrayList;
import java.util.List;

public class Mesero extends Empleado
{
	//Atributos
	private List<String> JUEGOSDOMINADOS = new ArrayList<>();
	
	//Cnstructor
	public Mesero(int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(id, nombre, email, login, password, codigoDesceunto, estaEnTurno);
	}

	//Metodos
	public List<String> getJUEGOSDOMINADOS() {
		return JUEGOSDOMINADOS;
	}

	public void setJUEGOSDOMINADOS(List<String> jUEGOSDOMINADOS) {
		JUEGOSDOMINADOS = jUEGOSDOMINADOS;
	}
	
	public double operacionesCaja(double dinero)
	{
		return 1.2;
	}
	
	@Override
	public String toLineaTxt() {
		
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