package interfas;

import java.io.*;
import java.util.ArrayList;

import administration.Historial;
import sales.Venta;
import services.Prestamo;
import products.Producto;
import products.JuegoDeMesa;

public class GestorHistorial {
    private Historial historial;
    private GestorInventario gestorInv;
    private static final String ARCHIVO_VENTAS = "archivosTxt/ventas.txt";

    public GestorHistorial(GestorInventario gestorInv) {
        this.historial = new Historial();
        this.gestorInv = gestorInv;
        cargarVentas();
    }

    private void cargarVentas() {
        File file = new File(ARCHIVO_VENTAS);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("VENTA")) {
                    historial.agregarVenta(reconstruirVenta(linea));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar historial de ventas: " + e.getMessage());
        }
    }

    private Venta reconstruirVenta(String linea) {
        String[] d = linea.split(",");
        
        int id = Integer.parseInt(d[1]);
        String fecha = d[2];
        boolean tienePropina = Boolean.parseBoolean(d[3]);
        double subtotalGuardado = Double.parseDouble(d[4]);

        Venta v = new Venta(id, fecha, tienePropina);
        v.setSubtotal(subtotalGuardado);

        if (d.length > 5 && !d[5].equals("VACIO")) {
            String[] idsProductos = d[5].split("-");
            for (String idProd : idsProductos) {
                Producto p = gestorInv.buscarProductoPorId(Integer.parseInt(idProd));
                if (p != null) v.agregarProducto(p);
            }
        }
        return v;
    }

    public Historial getHistorial() {
        return historial;
    }
}