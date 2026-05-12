package users;

public class Cocinero extends Empleado
{
	//Constructor
	public Cocinero(String rol, int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(rol, id, nombre, email, login, password, codigoDesceunto, estaEnTurno);
	}
	
	//Metodos
	@Override
	public String toLineaTxt() {
		
		return String.format("%s,%d,%s,%s,%s,%s,%s,%b",
				rol,
				id,
				nombre,
				email,
				login,
				password,
				codigoDescuento,
				estaEnTurno);
	}	
}