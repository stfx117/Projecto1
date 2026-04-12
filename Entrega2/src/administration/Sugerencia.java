package administration;

import interfas.GuardadoTxt;
import users.Empleado;

public class Sugerencia implements GuardadoTxt
{
	//Atributos
	private int id;
	private Empleado empleado;
	private String mensaje;
	private boolean leida;
	
	//Constructor 
	public Sugerencia(int id, Empleado empleado, String mensaje)
	{
		this.setId(id);
		this.setEmpleado(empleado);
		this.setMensaje(mensaje);
		this.setLeida(false);
	}

	//Metodos
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Empleado getEmpleado() 
	{
		return empleado;
	}

	public void setEmpleado(Empleado empleado) 
	{
		this.empleado = empleado;
	}

	public String getMensaje() 
	{
		return mensaje;
	}

	public void setMensaje(String mensaje) 
	{
		this.mensaje = mensaje;
	}

	public boolean isLeida() 
	{
		return leida;
	}

	public void setLeida(boolean leida) 
	{
		this.leida = leida;
	}

	@Override
	public String toLineaTxt() {
		return String.format("id, empleado, mensaje, leida, ",
                id,
                empleado.getId(),               
                mensaje.replace(",", ""),    
                leida);
	}
}