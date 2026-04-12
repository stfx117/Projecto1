package inventory;

import java.util.List;

import products.JuegoDeMesa;
import products.Producto;

public class Inventario {

    private List<JuegoDeMesa> juegosVenta;
    private List<JuegoDeMesa> juegosPrestamo;
    private List<Producto> productosIngeribles;

    public Inventario(List<JuegoDeMesa> juegosVenta, List<JuegoDeMesa> juegosPrestamo, List<Producto> productosIngeribles) {
        this.juegosVenta = juegosVenta;
        this.juegosPrestamo = juegosPrestamo;
        this.productosIngeribles = productosIngeribles;
    }

    public void agregarJuegoVenta(JuegoDeMesa j) {
        juegosVenta.add(j);
    }

    public void agregarJuegoPrestamo(JuegoDeMesa j) {
        juegosPrestamo.add(j);
    }

    public void agregarProductoIngerible(Producto p) {
        productosIngeribles.add(p);
    }

    public void eliminarProductoJuegoVenta(JuegoDeMesa j) {
        juegosVenta.remove(j);
    }
    
    public void eliminarProductoJuegoPrestamo(JuegoDeMesa j) {
        juegosPrestamo.remove(j);
    }
    
    public void eliminarProductoIngerible(Producto p) {
        productosIngeribles.remove(p);
    }

    public List<JuegoDeMesa> getJuegosVenta() {
        return juegosVenta;
    }

    public List<JuegoDeMesa> getJuegosPrestamo() {
        return juegosPrestamo;
    }

    public List<Producto> getProductosIngeribles() {
        return productosIngeribles;
    }

}