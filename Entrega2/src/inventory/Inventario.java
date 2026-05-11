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
    
    public void eliminarProductoJuegoVenta(JuegoDeMesa juego)
    {
    	if (juegosVenta.size() > 0) {
        	for(int i = 0; i < this.juegosVenta.size(); i++){
        		if(this.juegosVenta.get(i).getId() == juego.getId()) {
        			this.juegosVenta.remove(i);
        		}
        	}
        }
    }
    
    public void eliminarProductoJuegoVenta(int j) {
        juegosVenta.remove(j);
    }
    
    public void eliminarProductoJuegoPrestamo(int j) {
        juegosPrestamo.remove(j);
    }
    
    public void eliminarProductoIngerible(int p) {
        productosIngeribles.remove(p);
    }

    public ArrayList<JuegoDeMesa> getJuegosVenta()
    {
    	return this.juegosVenta;
    }
    
    public ArrayList<JuegoDeMesa> getJuegosPrestamo()
    {
    	return this.juegosPrestamo;
    }
    
    public JuegoDeMesa getJuegosVenta(int index) {
        if (juegosVenta.size() > 0) {
        	if (juegosVenta.size() > index) {
        		return juegosVenta.get(index);
        	}
        }
        return null;
    }

    public JuegoDeMesa getJuegosPrestamo(int index) {
    	if (juegosPrestamo.size() > 0) {
        	if (juegosPrestamo.size() > index) {
        		return juegosPrestamo.get(index);
        	}
        }
        return null;
    }

    public ProductoIngerible getProductosIngeribles(int index) {
    	if (productosIngeribles.size() > 0) {
        	if (productosIngeribles.size() > index) {
        		return productosIngeribles.get(index);
        	}
        }
        return null;
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