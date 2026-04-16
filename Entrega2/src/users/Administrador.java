package users;

import java.util.ArrayList;
import java.util.List;
import administration.Historial;
import administration.Sugerencia;
import inventory.Inventario;
import products.JuegoDeMesa;
import products.Producto;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;
import sales.Venta;
import services.Prestamo;

public class Administrador extends Usuario
{
	//Atributos
	private List<Sugerencia> buzonSugerencias = new ArrayList<>();
	
	//Constructor
	public Administrador(int id, String nombre, String email, String login, String password)
	{
		super(id, nombre, email, login, password);
	}
	
	//Metodos
	public Venta buscarVenta(Historial ventas, int id)
	{
		return ventas.getVenta(id);
	}
	
	public Prestamo buscarPrestamo(Historial prestamos, int id)
	{
		return prestamos.getPrestamo(id);
	}
	
	public void moverjuego(Inventario inventario, int id)
	{
		List<JuegoDeMesa> venta = inventario.getJuegosVenta();
		for(int i = 0; i < venta.size(); i++)
		{
			JuegoDeMesa juego = venta.get(i);
			if (juego.getId() == id)
			{
				juego.setParaVenta(false);
				inventario.agregarJuegoPrestamo(juego);
				inventario.eliminarProductoJuegoVenta(juego);
			}
		}
	}
	
	public void comprarJuego(Inventario inventario, int id, String nombre, double precioBase, String descripcion, int anioPublicacion, String empresa, int minJugadores, int maxJugadores, restriccionEdad restriccionEdad, boolean esDificil, EstadoFisico estadoFisico, Categoria categoria, boolean paraVenta)
	{
		JuegoDeMesa nuevoJuego = new JuegoDeMesa(id, nombre, precioBase, descripcion, anioPublicacion, empresa, minJugadores, maxJugadores, restriccionEdad, esDificil, estadoFisico, categoria, paraVenta);
		
		if(nuevoJuego.isParaVenta() && nuevoJuego.getEstadoFisico() == EstadoFisico.NUEVO)
		{
			inventario.agregarJuegoVenta(nuevoJuego);
		} else
		{
			inventario.agregarJuegoPrestamo(nuevoJuego);
		}
	}
	
	public void reparaJuego(Inventario inventario, int id)
	{
		List<JuegoDeMesa> prestamo = inventario.getJuegosPrestamo();
		for(int i = 0; i < prestamo.size(); i++)
		{
			JuegoDeMesa juego = prestamo.get(i);
			if (juego.getId() == id)
			{
				juego.setEstadoFisico(EstadoFisico.OPTIMO);
			}
		}
	}
	
	public void cambiarEstado(Inventario inventario, int id, EstadoFisico nuevoEstadoFisico)
	{
		List<JuegoDeMesa> prestamo = inventario.getJuegosPrestamo();
		for(int i = 0; i < prestamo.size(); i++)
		{
			JuegoDeMesa juego = prestamo.get(i);
			if (juego.getId() == id)
			{
				juego.setEstadoFisico(nuevoEstadoFisico);
			}
		}
	}
	
	public void agregarProducto(Inventario inventario, Producto nuevoProducto)
	{
		inventario.agregarProductoIngerible(nuevoProducto);
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

	public List<Sugerencia> getBuzonSugerencias() 
	{
		return buzonSugerencias;
	}

	public void setBuzonSugerencias(List<Sugerencia> buzonSugerencias)
	{
		this.buzonSugerencias = buzonSugerencias;
	}
	
	public void recibirSugerencia(Sugerencia s)
	{
        buzonSugerencias.add(s);
        System.out.println("Nueva sugerencia de " + s.getEmpleado().getNombre());
    }

    public void verSugerenciasPendientes() 
    {
        for (Sugerencia s : buzonSugerencias)
        {
            if (!s.isLeida()) 
            {
                System.out.println("- " + s.getMensaje());
            }
        }
    }
}	