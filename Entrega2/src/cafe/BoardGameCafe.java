package cafe;

import java.util.HashMap;
import java.util.Map;

public class BoardGameCafe
{
	//Atributos
	private String nombre;
	private Map<String, String> configuracion = new HashMap<>();
	private  static BoardGameCafe instancia;
	
	//Constructor
	private BoardGameCafe(String nombre)
	{
		this.nombre = nombre;	
	}
	
	//Metodos
	public String getNombre() 
	{
	    return nombre;
	}
	
	public Map<String, String> getConfiguracion() {
	    return configuracion;
	}

	/**
	 * permite agregar un nuevo dato a la tabla hashmap
	 * @param llave
	 * @param valor
	 */
	public void agregarConfiguracion(String llave, String valor) 
	{
	    configuracion.put(llave, valor);
	}

	/**
	 * permite obterner el valor de una llave dada
	 * @param llave
	 * @return
	 */
	public String obtenerConfiguracion(String llave) 
	{
	    return configuracion.get(llave);
	}
	
	/*Permite tener una unica instancia del cafe
	 * 
	 */
	public static BoardGameCafe getInstancia(String nombre) 
	{
        if (instancia == null) 
        {
            instancia = new BoardGameCafe(nombre);
        }
        return instancia;
    }
	
}