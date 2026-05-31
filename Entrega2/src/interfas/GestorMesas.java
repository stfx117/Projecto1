package interfas;

import java.io.*;
import java.util.*;
import services.Mesa;

public class GestorMesas {
    private List<Mesa> listaMesas;
    private GestorArchivo gestorA;
    private static final String ARCHIVO_MESAS = "mesas.txt";
    private static final String RUTA_COMPLETA = "archivosTxt/mesas.txt";

    public GestorMesas(GestorArchivo gestorA) {
        this.listaMesas = new ArrayList<>();
        this.gestorA = gestorA;
        cargarMesas();
    }

    private void cargarMesas() {
        File file = new File(RUTA_COMPLETA);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0].trim());
                int capacidad = Integer.parseInt(partes[1].trim());
                boolean ocupada = Boolean.parseBoolean(partes[2].trim());

                Mesa mesa = new Mesa(id, capacidad, ocupada);
                listaMesas.add(mesa);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo de mesas: " + e.getMessage());
        }
    }

    public void registrarNuevaMesa(Mesa nuevaMesa) {
        listaMesas.add(nuevaMesa);
        try {
            gestorA.guardarEntidad(nuevaMesa, ARCHIVO_MESAS);
        } catch (IOException e) {
            System.err.println("Error al escribir la nueva mesa en el archivo.");
            e.printStackTrace();
        }
    }

    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_COMPLETA, false))) {
            for (Mesa m : listaMesas) {
                pw.println(m.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de mesas: " + e.getMessage());
        }
    }

    public Mesa buscarMesaPorId(int idMesa) {
        for (Mesa m : listaMesas) {
            if (m.getId() == idMesa) {
                return m;
            }
        }
        return null;
    }

    public List<Mesa> getMesasDisponibles() {
        List<Mesa> disponibles = new ArrayList<>();
        for (Mesa m : listaMesas) {
            if (!m.getEstaOcupada()) {
                disponibles.add(m);
            }
        }
        return disponibles;
    }

    public List<Mesa> getListaMesas() {
        return listaMesas;
    }
}