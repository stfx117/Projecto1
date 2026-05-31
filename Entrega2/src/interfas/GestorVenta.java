package interfas;

import java.io.*;
import java.util.*;
import sales.Venta;
import products.Producto;

public class GestorVenta {
    private List<Venta> listaVentas;
    private GestorArchivo gestorA;
    private GestorInventario gestorI;
    private static final String ARCHIVO_VENTAS = "ventas.txt";
    private static final String RUTA_COMPLETA = "archivosTxt/ventas.txt";

    public GestorVenta(GestorInventario gestorI, GestorArchivo gestorA) {
        this.listaVentas = new ArrayList<>();
        this.gestorI = gestorI;
        this.gestorA = gestorA;
        cargarVentas();
    }

    private void cargarVentas() {
        File file = new File(RUTA_COMPLETA);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] partes = linea.split(",");
                
                int idVenta = Integer.parseInt(partes[1]);
                String fecha = partes[2];
                boolean propina = Boolean.parseBoolean(partes[3]);
                double subtotal = Double.parseDouble(partes[4]);
                
                Venta venta = new Venta(idVenta, fecha, propina);
                venta.setSubtotal(subtotal);

                if (partes.length > 5 && !partes[5].equals("VACIO")) {
                    String[] idsProductos = partes[5].split("-");
                    for (String idStr : idsProductos) {
                        int idProd = Integer.parseInt(idStr);
                        
                        Producto productoReal = gestorI.buscarProductoPorId(idProd);
                        
                        if (productoReal != null) {
                            venta.agregarProducto(productoReal);
                        }
                    }
                }
                
                listaVentas.add(venta);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo de ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registrarVenta(Venta v) {
        listaVentas.add(v);
        try {
            gestorA.guardarEntidad(v, ARCHIVO_VENTAS);
        } catch (IOException e) {
            System.err.println("No se pudo escribir la venta en el archivo físico.");
            e.printStackTrace();
        }
    }

    public int generarNuevoId() {
        int max = 0;
        for (Venta v : listaVentas) {
            if (v.getId() > max) max = v.getId();
        }
        return max + 1;
    }
    
    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_COMPLETA, false))) {
            for (Venta v : listaVentas) {
                pw.println(v.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de ventas: " + e.getMessage());
        }
    }

    public double calcularIngresosTotales() {
        double ingresos = 0;
        for (Venta v : listaVentas) {
            ingresos += v.getSubtotal();
        }
        return ingresos;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }
}