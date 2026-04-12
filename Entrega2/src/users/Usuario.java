package users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import interfas.GuardadoTxt;

public abstract class Usuario implements GuardadoTxt
{
	//Atributos
	protected int id;
	protected String nombre;
	protected String email;
	protected String login;
	protected String password;
	
	//Constructor
	public Usuario(int id, String nombre, String email, String login, String password)
	{
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.login = login;
		this.password = password;
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

	public String getNombre() 
	{
	    return nombre;
	}

	public void setNombre(String nombre) 
	{
	    this.nombre = nombre;
	}

	public String getEmail() 
	{
	    return email;
	}

	public void setEmail(String email) 
	{
	    this.email = email;
	}

	public String getLogin() 
	{
	    return login;
	}

	public void setLogin(String login) 
	{
	    this.login = login;
	}

	public String getPassword() 
	{
	    return password;
	}

	public void setPassword(String password) 
	{
	    this.password = password;
	}
	
	
	/*Permite al usuario iniciar secion en la aplicacionque recive por parametros ruta del archivo tx, login y password, 
	 * retorna false si el login o la contraseña son incorrectos y true si son correctos
	 */
	public boolean inicio(String rutaArchivoTxt,String login, String password)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoTxt))){
			String linea;
			while((linea = br.readLine()) != null)
			{
				String[] datos = linea.split(",");
				
				String loginArchivo = datos[3];
				String passwordArchivo = datos[4];
				if(loginArchivo.equals(login) && passwordArchivo.equals(password))
					return true;
			}
		} catch(IOException e) {
			System.err.println("Error al leer el archivo");
		}
		
		return false;
	}
}	