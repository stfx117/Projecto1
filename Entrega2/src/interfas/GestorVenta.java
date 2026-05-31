package interfas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import products.Producto;
import sales.Venta;

public class GestorVenta {
    private HashMap<Integer, Venta> mapaVentas;
    private GestorInventario gestorI;

    public GestorVenta() {
        this.mapaVentas = new HashMap<>();
        this.gestorI = new GestorInventario();
        cargarVentasDesdeArchivo();
    }

    public void agregarVenta(Venta v) {
        mapaVentas.put(v.getId(), v);
    }

    public Venta buscarVenta(int id) {
        return mapaVentas.get(id);
    }

    public HashMap<Integer, Venta> getMapaVentas() {
        return mapaVentas;
    }

    private void cargarVentasDesdeArchivo() {
        String ruta = "archivosTxt/ventas.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length < 6) {
                    System.err.println("Saltando línea mal formateada: " + linea);
                    continue;
                }

                try {
                    String tipo = datos[0].trim();
                    if (!tipo.equalsIgnoreCase("VENTA")) {
                        continue;
                    }

                    int idVenta = Integer.parseInt(datos[1].trim());
                    String fecha = datos[2].trim();
                    boolean propina = Boolean.parseBoolean(datos[3].trim());
                    double subtotal = Double.parseDouble(datos[4].trim());
                    Venta venta = new Venta(idVenta, fecha, propina);
                    venta.setSubtotal(subtotal);
                    String productosTxt = datos[5].trim();
                    if (!productosTxt.equalsIgnoreCase("VACIO")) {
                        String[] idsProductos = productosTxt.split("-");
                        for (String idProductoStr : idsProductos) {
                            int idProducto = Integer.parseInt(idProductoStr);
                            Producto producto = gestorI.buscarProductoPorId(idProducto);
                            if (producto != null) {
                                venta.agregarProducto(producto);
                            }
                        }
                    }
                    mapaVentas.put(idVenta, venta);

                } catch (Exception e) {
                    System.err.println("Error cargando venta: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println(
                    "No se pudo cargar el archivo de ventas."
            );
        }
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Venta v : mapaVentas.values()) {
            if (v.getId() > maxId) {
                maxId = v.getId();
            }
        }
        return maxId + 1;
    }

    public Venta buscarVentaPorId(int id) {
        return mapaVentas.get(id);
    }

    public void actualizarArchivo() {
        String ruta = "archivosTxt/ventas.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Venta v : mapaVentas.values()) {
                pw.println(v.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo ventas: " + e.getMessage());
        }
    }
}