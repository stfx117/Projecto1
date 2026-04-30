package inventory;

import java.util.ArrayList;

import products.JuegoDeMesa;
import products.ProductoIngerible;

public class Inventario {

    private ArrayList<JuegoDeMesa> juegosVenta;
    private ArrayList<JuegoDeMesa> juegosPrestamo;
    private ArrayList<ProductoIngerible> productosIngeribles;

    public Inventario() {
        this.juegosVenta = new ArrayList<JuegoDeMesa>();
        this.juegosPrestamo = new ArrayList<JuegoDeMesa>();
        this.productosIngeribles = new ArrayList<ProductoIngerible>();
    }

    public void agregarJuegoVenta(JuegoDeMesa j) {
        juegosVenta.add(j);
    }

    public void agregarJuegoPrestamo(JuegoDeMesa j) {
        juegosPrestamo.add(j);
    }

    public void agregarProductoIngerible(ProductoIngerible p) {
        productosIngeribles.add(p);
    }

    public void eliminarProductoJuegoVenta(JuegoDeMesa j) {
        juegosVenta.remove(j);
    }
    
    public void eliminarProductoJuegoPrestamo(JuegoDeMesa j) {
        juegosPrestamo.remove(j);
    }
    
    public void eliminarProductoIngerible(ProductoIngerible p) {
        productosIngeribles.remove(p);
    }

    public JuegoDeMesa getJuegosVenta(int index) {
        return juegosVenta.get(index);
    }

    public JuegoDeMesa getJuegosPrestamo(int index) {
        return juegosPrestamo.get(index);
    }

    public ProductoIngerible getProductosIngeribles(int index) {
        return productosIngeribles.get(index);
    }
    
    public void vaciarJuegosVenta() {
    	juegosVenta.clear();
    }
    
    public void vaciarJuegosPrestamo() {
    	juegosPrestamo.clear();
    }
    
    public void vaciarProductosIngeribles() {
    	productosIngeribles.clear();
    }

}