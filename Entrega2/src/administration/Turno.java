package administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import users.Empleado;


public class Turno
{
	private Map<String, List<String>> DIASEMANAHORA = new HashMap<>();
	private Empleado empleado;
	
	//cosntructor
	public Turno(Empleado empleado)
	{
		this.empleado = empleado;;
	}

	public Map<String, List<String>> getDIASEMANAHORA() {
		return DIASEMANAHORA;
	}

	public void setDIASEMANAHORA(Map<String, List<String>> dIASEMANAHORA) {
		DIASEMANAHORA = dIASEMANAHORA;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
}