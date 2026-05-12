package interfas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import administration.Sugerencia;
import users.Empleado;
import users.Usuario;

public class GestorSugerencias {
    private List<Sugerencia> sugerencias;
    private GestorUsuario gestorU;
    private static final String ARCHIVO = "archivosTxt/sugerencias.txt";
    private GestorArchivo gestorA;

    public GestorSugerencias(GestorUsuario gestorU) {
        this.sugerencias = new ArrayList<>();
        this.gestorU = gestorU;
        cargarSugerencias();
    }

    private void cargarSugerencias() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sugerencias.add(reconstruirSugerencia(linea));
            }
        } catch (IOException e) {
            System.err.println("Error al cargar sugerencias: " + e.getMessage());
        }
    }

    private Sugerencia reconstruirSugerencia(String linea) {
        String[] d = linea.split(",");
        
        int id = Integer.parseInt(d[1]);
        int idEmp = Integer.parseInt(d[1]);
        String mensaje = d[2];
        boolean leida = Boolean.parseBoolean(d[3]);

        Usuario u = gestorU.buscarUsuarioPorId(idEmp);
        
        Sugerencia s = new Sugerencia(id, (Empleado) u, mensaje);
        s.setLeida(leida);
        return s;
    }

    public void guardarNuevaSugerencia(Sugerencia s) {
        sugerencias.add(s);
        try {
			gestorA.guardarEntidad(s, "sugerencias.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void actualizarArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Sugerencia s : sugerencias) {
                pw.println(s.toLineaTxt());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar sugerencias: " + e.getMessage());
        }
    }

    public List<Sugerencia> getSugerencias() {
        return this.sugerencias;
    }
    
    public int generarNuevoId() {
        int max = 0;
        for (Sugerencia s : sugerencias) {
            if (s.getId() > max) max = s.getId();
        }
        return max + 1;
    }
}