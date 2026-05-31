package interfas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import products.JuegoDeMesa;
import services.Mesa;
import services.Prestamo;
import users.Cliente;

public class GestorPrestamoAlternativa {
    private HashMap<Integer, Prestamo> mapaPrestamos;
    private GestorUsuario gestorU;
    private GestorInventario gestorI;

    public GestorPrestamoAlternativa() {
        this.mapaPrestamos = new HashMap<>();
        this.gestorU = new GestorUsuario();
        this.gestorI = new GestorInventario();
        cargarPrestamosDesdeArchivo();
    }

    public void agregarPrestamo(Prestamo p) {
        mapaPrestamos.put(p.getId(), p);
    }

    public Prestamo buscarPrestamo(int id) {
        return mapaPrestamos.get(id);
    }

    public HashMap<Integer, Prestamo> getMapaPrestamos() {
        return mapaPrestamos;
    }

    private void cargarPrestamosDesdeArchivo() {
        String ruta = "archivosTxt/prestamos.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length < 5) {
                    System.err.println("Saltando línea mal formateada: " + linea);
                    continue;
                }
                try {
                    int idPrestamo = Integer.parseInt(datos[0].trim());
                    int idMesa = Integer.parseInt(datos[1].trim());
                    boolean advertencia = Boolean.parseBoolean(datos[2].trim());
                    int idCliente = Integer.parseInt(datos[3].trim());
                    Cliente cliente = (Cliente) gestorU.buscarUsuarioPorId(idCliente);

                    if (cliente == null) {
                        continue;
                    }

                    Mesa mesa = new Mesa(idMesa, 4, false);
                    Prestamo prestamo = new Prestamo(idPrestamo, mesa, advertencia, cliente);
                    String juegosTxt = datos[4].trim();

                    if (!juegosTxt.equalsIgnoreCase("NONE")) {
                        String[] idsJuegos = juegosTxt.split("-");
                        for (String idJuegoStr : idsJuegos) {
                            int idJuego = Integer.parseInt(idJuegoStr);
                            Object producto = gestorI.buscarProductoPorId(idJuego);
                            if (producto instanceof JuegoDeMesa) {
                                prestamo.agregarJuego((JuegoDeMesa) producto);
                            }
                        }
                    }

                    mapaPrestamos.put(idPrestamo, prestamo);
                } catch (Exception e) {
                    System.err.println(
                            "Error cargando préstamo: " + linea
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de préstamos.");
        }
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Prestamo p : mapaPrestamos.values()) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }
        return maxId + 1;
    }

    public Prestamo buscarPrestamoPorId(int id) {
        return mapaPrestamos.get(id);
    }

    public void actualizarArchivo() {
        String ruta = "archivosTxt/prestamos.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Prestamo p : mapaPrestamos.values()) {
                pw.println(p.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo préstamos: "+ e.getMessage());
        }
    }
}