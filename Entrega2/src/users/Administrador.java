package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import administration.Historial;
import administration.Sugerencia;
import inventory.Inventario;
import products.JuegoDeMesa;
import products.ProductoIngerible;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;
import products.Producto;
import sales.Venta;
import services.Prestamo;

public class Administrador extends Usuario
{
	//Atributos
	private List<Sugerencia> buzonSugerencias = new ArrayList<>();
	
	//Constructor
	public Administrador(String rol, int id, String nombre, String email, String login, String password)
	{
		super(rol, id, nombre, email, login, password);
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
	
	public void agregarProducto(Inventario inventario, ProductoIngerible nuevoProducto)
	{
		inventario.agregarProductoIngerible(nuevoProducto);
	}
	
	@Override
	public String toLineaTxt()
	{
		return String.format("%s,%d,%s,%s,%s,%s",
				rol,
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
    
    public Map<String, Double> informeEstado(Historial h, LocalDate inicio, LocalDate fin)
    {
    	double juegos = 0; 
    	double comida = 0;
    	double impuestos = 0; 
    	double propinas = 0;
    	
    	ArrayList<Venta> lista = h.listaVentas();
    	
    	for (Venta v : lista) {
            LocalDate fechaVenta = LocalDate.parse(v.getFecha()); 

            if (!fechaVenta.isBefore(inicio) && !fechaVenta.isAfter(fin)) {
                
                impuestos += v.getSubtotal() * 0.19; 
                if (v.getPropina()) {
                    propinas += v.getSubtotal() * 0.10;
                }

                for (Producto p : v.getItems()) {
                    if (p instanceof JuegoDeMesa) {
                        juegos += p.getPrecioBase();
                    } else {
                        comida += p.getPrecioBase();
                    }
                }
            }
        }
    	
    	Map<String, Double> resultados = new HashMap<>();
        resultados.put("juegos", juegos);
        resultados.put("comida", comida);
        resultados.put("impuestos", impuestos);
        resultados.put("propinas", propinas);
        resultados.put("totalNeto", juegos + comida + impuestos + propinas);

        return resultados;
    }
}	