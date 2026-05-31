package interfas;

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
    }
}