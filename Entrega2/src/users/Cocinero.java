package users;

public class Cocinero extends Empleado
{
	//Constructor
	public Cocinero(int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(id, nombre, email, login, password, codigoDesceunto, estaEnTurno);
	}
	
	//Metodos
	@Override
	public String toLineaTxt() {
		
		return String.format("id, nombre, email, login, password, codigoDesceunto, estaEnTurno",
				id,
				nombre,
				email,
				login,
				password,
				codigoDescuento,
				estaEnTurno);
	}	
}