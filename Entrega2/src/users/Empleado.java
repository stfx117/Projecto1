package users;

public abstract class Empleado extends Usuario
{
	//Atributos
	protected String codigoDescuento;
	protected boolean estaEnTurno;
	
	//Constructor
	public Empleado(int id, String nombre, String email, String login, String password, String codigoDesceunto, boolean estaEnTurno)
	{
		super(id, nombre, email, login, password);
		this.codigoDescuento = codigoDesceunto;
		this.estaEnTurno = estaEnTurno;
	}

	//Metodos
	public String getCodigoDescuento() 
	{
		return codigoDescuento;
	}

	public void setCodigoDescuento(String codigoDescuento) 
	{
		this.codigoDescuento = codigoDescuento;
	}

	public boolean isEstaEnTurno() 
	{
		return estaEnTurno;
	}

	public void setEstaEnTurno(boolean estaEnTurno) 
	{
		this.estaEnTurno = estaEnTurno;
	}
	
	public void realizarSugerencia(String sugerencia)
	{
		
	}
}