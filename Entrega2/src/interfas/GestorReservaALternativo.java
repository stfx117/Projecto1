package interfas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import services.Mesa;
import services.Reserva;

public class GestorReservaALternativo {

    private HashMap<Integer, Reserva> mapaReservas;
    private GestorUsuario gestorU;

    public GestorReservaALternativo() {
        this.mapaReservas = new HashMap<>();
        this.gestorU = new GestorUsuario();
        cargarReservasDesdeArchivo();
    }

    public void agregarReserva(Reserva r) {
        mapaReservas.put(r.getId(), r);
    }

    public Reserva buscarReserva(int id) {
        return mapaReservas.get(id);
    }

    private void cargarReservasDesdeArchivo() {
        String ruta = "archivosTxt/reservas.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length < 7) {
                    System.err.println("Saltando línea mal formateada: " + linea);
                    continue;
                }
                try {
                    int idReserva = Integer.parseInt(datos[0].trim());
                    int idCliente = Integer.parseInt(datos[1].trim());
                    int idMesa = Integer.parseInt(datos[2].trim());
                    int personas = Integer.parseInt(datos[3].trim());
                    boolean menores5 = Boolean.parseBoolean(datos[4].trim());
                    boolean hayJovenes = Boolean.parseBoolean(datos[5].trim());
                    String estadia = datos[6].trim();
                    users.Cliente cliente =
                            (users.Cliente) gestorU.buscarUsuarioPorId(idCliente);
                    if (cliente != null) {
                        Mesa mesa = new Mesa(idMesa, personas, false);
                        Reserva reserva = new Reserva(idReserva, cliente, mesa, personas, menores5, hayJovenes, estadia);
                        mapaReservas.put(idReserva, reserva);
                    }
                } catch (Exception e) {
                    System.err.println("Error cargando reserva: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de reservas.");
        }
    }

    public int generarNuevoId() {
        int maxId = 0;
        for (Reserva r : mapaReservas.values()) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        return maxId + 1;
    }

    public Reserva buscarReservaPorId(int id) {
        return mapaReservas.get(id);
    }

    public HashMap<Integer, Reserva> getMapaReservas() {
        return mapaReservas;
    }
    
    public void actualizarArchivo() {
        String ruta = "archivosTxt/reservas.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (Reserva r : mapaReservas.values()) {
                pw.println(r.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo reservas: " + e.getMessage());
        }
    }
}