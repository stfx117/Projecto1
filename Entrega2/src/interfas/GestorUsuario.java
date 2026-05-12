package interfas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import users.Administrador;
import users.Cliente;
import users.Cocinero;
import users.Mesero;
import users.Usuario;

public class GestorUsuario
{
	private HashMap<String, Usuario> mapaUsuarios;
	private GestorArchivo gestorA;

    public GestorUsuario() {
        this.mapaUsuarios = new HashMap<>();
        this.gestorA = new GestorArchivo();
        cargarUsuariosDesdeArchivo(); 
        
        if (mapaUsuarios.isEmpty()) {
            crearAdminPorDefecto();
        }
    }

    private void crearAdminPorDefecto() {
        Administrador adminBase = new Administrador("ADMIN", 1, "Admin General", "admin@cafe.com", "admin", "1234");

        mapaUsuarios.put(adminBase.getLogin(), adminBase);
        
        try {
            gestorA.guardarEntidad(adminBase, "usuarios.txt");
            System.out.println("Se ha generado un administrador por defecto (admin/1234).");
        } catch (IOException e) {
            System.err.println("No se pudo persistir el admin por defecto: " + e.getMessage());
        }
    }
    
    public void agregarUsuario(Usuario u) {
        mapaUsuarios.put(u.getLogin(), u);
    }

    public Usuario buscarUsuario(String login) {
        return mapaUsuarios.get(login);
    }
    
    private void cargarUsuariosDesdeArchivo() {
        String ruta = "archivosTxt/usuarios.txt";
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
                    String rol = datos[0].trim();
                    int id = Integer.parseInt(datos[1].trim());
                    String nombre = datos[2].trim();
                    String email = datos[3].trim();
                    String login = datos[4].trim();
                    String password = datos[5].trim();

                    Usuario u = null;
                    
                    switch (rol.toUpperCase()) {
                        case "ADMIN":    
                            u = new Administrador(rol, id, nombre, email, login, password); 
                            break;
                        case "MESERO":   
                            if (datos.length >= 8) {
                                String turno = datos[6].trim();
                                boolean estaDisponible = Boolean.parseBoolean(datos[7].trim());
                                u = new Mesero(rol, id, nombre, email, login, password, turno, estaDisponible);
                            }
                            break;
                        case "COCINERO": 
                            if (datos.length >= 8) {
                                String especialidad = datos[6].trim();
                                boolean enTurno = Boolean.parseBoolean(datos[7].trim());
                                u = new Cocinero(rol, id, nombre, email, login, password, especialidad, enTurno);
                            }
                            break;
                        case "CLIENTE":  
                            if (datos.length >= 9) {
                                String fechaNac = datos[6].trim();
                                int puntos = Integer.parseInt(datos[7].trim());
                                String cod = datos[8].trim();
                                u = new Cliente(rol, id, nombre, email, login, password, fechaNac, puntos, cod);
                            }
                            break;
                    }

                    if (u != null) {
                        mapaUsuarios.put(login, u);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de usuarios.");
        }
    }

	public Object getMapaUsuarios() {
		return this.mapaUsuarios;
	}
	
	public int generarNuevoId() {
	    int maxId = 0;
	    
	    for (Usuario u : mapaUsuarios.values()) {
	        if (u.getId() > maxId) {
	            maxId = u.getId();
	        }
	    }
	    
	    return maxId + 1;
	}
	
	public Usuario buscarUsuarioPorId(int id) {
	    for (Usuario u : mapaUsuarios.values()) {
	        if (u.getId() == id) {
	            return u;
	        }
	    }
	    return null; 
	}
	
	public void actualizarArchivo() {
	    String ruta = "archivosTxt/usuarios.txt";
	    
	    try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
	        
	        for (Usuario u : mapaUsuarios.values()) {
	            pw.println(u.toLineaTxt());
	        }
	        
	    } catch (IOException e) {
	        System.err.println("Error al actualizar el archivo de usuarios: " + e.getMessage());
	    }
	}
}