package interfas;

import java.io.*;
import java.util.*;

import administration.Turno;
import users.Empleado;

public class GestorTurno {
    private List<Turno> listaTurnos;
    private GestorUsuario gestorU;
    private static final String ARCHIVO = "archivosTxt/turnos.txt";

    public GestorTurno(GestorUsuario gestorU) {
        this.listaTurnos = new ArrayList<>();
        this.gestorU = gestorU;
        cargarTurnos();
    }

    private void cargarTurnos() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("TURNO")) {
                    listaTurnos.add(reconstruirTurno(linea));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar turnos: " + e.getMessage());
        }
    }

    private Turno reconstruirTurno(String linea) {
        String[] partes = linea.split(",");
        int idEmp = Integer.parseInt(partes[1].trim());
        
        Empleado emp = (Empleado) gestorU.buscarUsuarioPorId(idEmp);
        Turno turno = new Turno(emp);

        if (partes.length > 2) {

            String[] bloquesDias = partes[2].split(";");
            for (String bloque : bloquesDias) {

                String[] diaYHoras = bloque.split(":");
                String dia = diaYHoras[0];
                

                String[] horasArray = diaYHoras[1].split("-");
                List<String> listaHoras = new ArrayList<>(Arrays.asList(horasArray));
                
                turno.getDIASEMANAHORA().put(dia, listaHoras);
            }
        }
        return turno;
    }

    public void guardarTurno(Turno t) {

        listaTurnos.removeIf(turno -> turno.getEmpleado().getId() == t.getEmpleado().getId());
        listaTurnos.add(t);
        actualizarArchivo();
    }

    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Turno t : listaTurnos) {
                pw.println(t.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar turnos: " + e.getMessage());
        }
    }

    public Turno buscarTurnoPorEmpleado(int idEmpleado) {
        for (Turno t : listaTurnos) {
            if (t.getEmpleado().getId() == idEmpleado) return t;
        }
        return null;
    }
}