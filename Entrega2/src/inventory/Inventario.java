package inventory;

import java.util.List;

import products.Producto;

public class Inventario {

    private String ultimaActualizacion;
    private List<Producto> juegosVenta;
    private List<Producto> juegosPrestamo;
    private List<Producto> productosIngeribles;

    public Inventario(String ultimaActualizacion, List<Producto> juegosVenta, List<Producto> juegosPrestamo, List<Producto> productosIngeribles) {
        this.ultimaActualizacion = ultimaActualizacion;
        this.juegosVenta = juegosVenta;
        this.juegosPrestamo = juegosPrestamo;
        this.productosIngeribles = productosIngeribles;
    }

    public void agregarJuegoVenta(Producto p, String ultimaFecha) {
        juegosVenta.add(p);
        actualizarFecha(ultimaFecha);
    }

    public void agregarJuegoPrestamo(Producto p, String ultimaFecha) {
        juegosPrestamo.add(p);
        actualizarFecha(ultimaFecha);
    }

    public void agregarProductoIngerible(Producto p, String ultimaFecha) {
        productosIngeribles.add(p);
        actualizarFecha(ultimaFecha);
    }

    public void eliminarProductoJuegoVenta(Producto p, String ultimaFecha) {
        juegosVenta.remove(p);
        actualizarFecha(ultimaFecha);
    }
    
    public void eliminarProductoJuegoPrestamo(Producto p, String ultimaFecha) {
        juegosPrestamo.remove(p);
        actualizarFecha(ultimaFecha);
    }
    
    public void eliminarProductoIngerible(Producto p, String ultimaFecha) {
        productosIngeribles.remove(p);
        actualizarFecha(ultimaFecha);
    }

    private void actualizarFecha(String ultimaFecha) {
        this.ultimaActualizacion = ultimaFecha;
    }

    public List<Producto> getJuegosVenta() {
        return juegosVenta;
    }

    public List<Producto> getJuegosPrestamo() {
        return juegosPrestamo;
    }

    public List<Producto> getProductosIngeribles() {
        return productosIngeribles;
    }
    
    public String getUltimaActualizacion() {
    	return this.ultimaActualizacion;
    }
}