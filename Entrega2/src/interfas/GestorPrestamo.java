package interfas;

import java.io.*;
import java.util.*;
import services.Prestamo;
import services.Mesa;
import users.Cliente;
import products.JuegoDeMesa;

public class GestorPrestamo {
    private List<Prestamo> listaPrestamos;
    private GestorArchivo gestorA;
    private GestorUsuario gestorU;
    private GestorMesas gestorM;
    private GestorInventario gestorI;
    private static final String ARCHIVO_PRESTAMOS = "prestamos.txt";
    private static final String RUTA_COMPLETA = "archivosTxt/prestamos.txt";

    public GestorPrestamo(GestorUsuario gestorU, GestorMesas gestorM, GestorInventario gestorI, GestorArchivo gestorA) {
        this.listaPrestamos = new ArrayList<>();
        this.gestorU = gestorU;
        this.gestorM = gestorM;
        this.gestorI = gestorI;
        this.gestorA = gestorA;
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        File file = new File(RUTA_COMPLETA);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                int idPrestamo = Integer.parseInt(partes[0].trim());
                int idMesa = Integer.parseInt(partes[1].trim());
                boolean advertencia = Boolean.parseBoolean(partes[2].trim());
                int idCliente = Integer.parseInt(partes[3].trim());

                Mesa mesaReal = gestorM.buscarMesaPorId(idMesa);
                Cliente clienteReal = (Cliente) gestorU.buscarUsuarioPorId(idCliente);

                if (mesaReal != null && clienteReal != null) {
                    Prestamo prestamo = new Prestamo(idPrestamo, mesaReal, advertencia, clienteReal);

                    if (partes.length > 4 && !partes[4].equals("NONE")) {
                        String[] idsJuegos = partes[4].split("-");
                        for (String idStr : idsJuegos) {
                            int idJuego = Integer.parseInt(idStr.trim());
                            
                            JuegoDeMesa juegoReal = (JuegoDeMesa) gestorI.buscarProductoPorId(idJuego);
                            if (juegoReal != null) {
                                prestamo.agregarJuego(juegoReal);
                            }
                        }
                    }
                    listaPrestamos.add(prestamo);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo de préstamos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registrarPrestamo(Prestamo nuevoPrestamo) {
        listaPrestamos.add(nuevoPrestamo);
        try {
            gestorA.guardarEntidad(nuevoPrestamo, ARCHIVO_PRESTAMOS);
        } catch (IOException e) {
            System.err.println("Error al escribir el préstamo en el archivo.");
            e.printStackTrace();
        }
    }

    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_COMPLETA, false))) {
            for (Prestamo p : listaPrestamos) {
                pw.println(p.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de préstamos: " + e.getMessage());
        }
    }

    public int generarNuevoId() {
        int max = 0;
        for (Prestamo p : listaPrestamos) {
            if (p.getId() > max) max = p.getId();
        }
        return max + 1;
    }

    public List<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }
}