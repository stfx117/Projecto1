package users;

public class Administrador extends Usuario
{
	//Constructor
	public Administrador(int id, String nombre, String email, String login, String password)
	{
		super(id, nombre, email, login, password);
	}
	
	//Metodos
	public Venta buscarVenta(int id)
	{
	
	}
	
	public Prestamo buscarPrestamo(int id)
	{
		
	}
	
	public void moverjuego(int id)
	{
		
	}
	
	
	public void comprarJuego()
	{
		
	}
	
	public void reparaJuego(int id)
	{
		
	}
	
	public void cambiarEstado(int id)
	{
		
	}
	
	public void agregarProducto(Producto nuevoProducto)
	{
		
	}
	
	
	public boolean aceptarSugerencia(Sugerencia nuevaSugerencia)
	{
		return false;
	}
	
	@Override
	public String toLineaTxt()
	{
		return String.format("id, nombre, email, login, password",
				id,
				nombre,
				email,
				login, 
				password);
	}
}	