package interfas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import inventory.Inventario;
import products.Bebida;
import products.JuegoDeMesa;
import products.Pasteleria;
import products.ProductoIngerible;

public class GestorInventario {
    private Inventario inventario;
    private GestorArchivo gestorA;

    public GestorInventario() {
        this.inventario = new Inventario();
        this.gestorA = new GestorArchivo();
        cargarProductos();
    }
    
    public void actualizarArchivoProductos() {
        String ruta = "archivosTxt/productos.txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta, false))) {
            
            for (JuegoDeMesa j : inventario.getJuegosVenta()) {
                pw.println(j.toLineaTxt());
            }

            for (JuegoDeMesa j : inventario.getJuegosPrestamo()) {
                pw.println(j.toLineaTxt());
            }

            for (ProductoIngerible p : inventario.getProductosIngeribles()) {
                pw.println(p.toLineaTxt());
            }
            
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de inventario.");
        }
    }
    
    private void cargarProductos() {
        File file = new File("archivosTxt/productos.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                String tipo = d[0];

                switch (tipo) {
                    case "BEBIDA":
                        inventario.agregarProductoIngerible(new Bebida(
                            Integer.parseInt(d[1]), d[2], Double.parseDouble(d[3]), 
                            d[4], Boolean.parseBoolean(d[5]), Boolean.parseBoolean(d[6])));
                        break;

                    case "PASTEL":
                        Pasteleria p = new Pasteleria(Integer.parseInt(d[1]), d[2], 
                                                      Double.parseDouble(d[3]), d[4]);
                        if (d.length > 5) {
                            String[] alergenos = d[5].split("-");
                            for (String a : alergenos) p.agregarAlergeno(a);
                        }
                        inventario.agregarProductoIngerible(p);
                        break;

                    case "JUEGO":
                        JuegoDeMesa j = new JuegoDeMesa(
                            Integer.parseInt(d[1]), d[2], Double.parseDouble(d[3]), d[4],
                            Integer.parseInt(d[5]), d[6], Integer.parseInt(d[7]), Integer.parseInt(d[8]),
                            JuegoDeMesa.restriccionEdad.valueOf(d[9]), Boolean.parseBoolean(d[10]),
                            JuegoDeMesa.EstadoFisico.valueOf(d[11]), JuegoDeMesa.Categoria.valueOf(d[12]),
                            Boolean.parseBoolean(d[13])
                        );
                        if (j.isParaVenta()) inventario.agregarJuegoVenta(j);
                        else inventario.agregarJuegoPrestamo(j);
                        break;
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al cargar inventario: " + e.getMessage());
        }
    }

	public Inventario getInventario() {
		return this.inventario;
	}
}