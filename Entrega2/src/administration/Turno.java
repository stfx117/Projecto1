package administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import interfas.GuardadoTxt;
import users.Empleado;


public class Turno implements GuardadoTxt
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

	@Override
	public String toLineaTxt() {
		StringBuilder sb = new StringBuilder();
	    sb.append(empleado.getId()).append(",");

	    for (Map.Entry<String, List<String>> entrada : DIASEMANAHORA.entrySet()) {
	        String dia = entrada.getKey();
	        List<String> horas = entrada.getValue();
	        
	        sb.append(dia).append(",");
	        sb.append(String.join("-", horas));
	    }

	    return sb.toString();
	}
}