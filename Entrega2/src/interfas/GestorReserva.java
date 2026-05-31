package interfas;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import services.Mesa;
import services.Reserva;

public class GestorReserva {

    private HashMap<Integer, Reserva> mapaReservas;
    private GestorUsuario gestorU;

    public GestorReserva() {
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
            System.err.println("Error al actualizar archivo reservas: "
                    + e.getMessage());
        }
=======
import java.io.*;
import java.util.*;
import services.Reserva;
import services.Mesa;
import users.Cliente;

public class GestorReserva {
    private List<Reserva> listaReservas;
    private GestorArchivo gestorA;
    private GestorUsuario gestorU;
    private GestorMesas gestorM;
    private static final String ARCHIVO_RESERVAS = "reservas.txt";
    private static final String RUTA_COMPLETA = "archivosTxt/reservas.txt";

    public GestorReserva(GestorUsuario gestorU, GestorMesas gestorM, GestorArchivo gestorA) {
        this.listaReservas = new ArrayList<>();
        this.gestorU = gestorU;
        this.gestorM = gestorM;
        this.gestorA = gestorA;
        cargarReservas();
    }

    // 1. CARGAR DEL ARCHIVO AL INICIAR EL SISTEMA
    private void cargarReservas() {
        File file = new File(RUTA_COMPLETA);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                int idReserva = Integer.parseInt(partes[0].trim());
                int idCliente = Integer.parseInt(partes[1].trim());
                int idMesa = Integer.parseInt(partes[2].trim());
                int personas = Integer.parseInt(partes[3].trim());
                boolean menores5 = Boolean.parseBoolean(partes[4].trim());
                boolean jovenes = Boolean.parseBoolean(partes[5].trim());
                String estadia = partes[6].trim();

                Cliente clienteReal = (Cliente) gestorU.buscarUsuarioPorId(idCliente);
                Mesa mesaReal = gestorM.buscarMesaPorId(idMesa);

                if (clienteReal != null && mesaReal != null) {
                    Reserva reserva = new Reserva(idReserva, clienteReal, mesaReal, personas, menores5, jovenes, estadia);
                    listaReservas.add(reserva);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo de reservas: " + e.getMessage());
        }
    }

    public void registrarReserva(Reserva nuevaReserva) {
        listaReservas.add(nuevaReserva);
        try {
            gestorA.guardarEntidad(nuevaReserva, ARCHIVO_RESERVAS);
        } catch (IOException e) {
            System.err.println("Error al escribir la reserva en el archivo.");
            e.printStackTrace();
        }
    }

    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_COMPLETA, false))) {
            for (Reserva r : listaReservas) {
                pw.println(r.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de reservas: " + e.getMessage());
        }
    }

    public int generarNuevoId() {
        int max = 0;
        for (Reserva r : listaReservas) {
            if (r.getId() > max) max = r.getId();
        }
        return max + 1;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
>>>>>>> refs/heads/Est-1
    }
}