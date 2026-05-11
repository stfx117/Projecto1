package interfas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import users.Administrador;
import users.Cliente;
import users.Cocinero;
import users.Mesero;
import users.Usuario;

public class GestorUsuario
{
	private Map<String, Usuario> mapaUsuarios;

    public GestorUsuario() {
        this.mapaUsuarios = new HashMap<>();
        cargarUsuariosDesdeArchivo(); 
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
                String[] datos = linea.split(",");
                String rol = datos[0];
                int id = Integer.parseInt(datos[1]);
                String nombre = datos[2];
                String email = datos[3];
                String login = datos[4];
                String password = datos[5];

                Usuario u = null;
                
                switch (rol.toUpperCase()) {
                    case "ADMIN":    
                    	u = new Administrador(rol, id, nombre, email, login, password); 
                    	break;
                    case "MESERO":   
                    	String codigoDesceunto = datos[6];
                    	boolean estaEnTurno = Boolean.parseBoolean(datos[7]);
                    	u = new Mesero(rol, id, nombre, email, login, password, codigoDesceunto, estaEnTurno); 
                    	break;
                    case "COCINERO": 
                    	String codigoDesceunto1 = datos[6];
                    	boolean estaEnTurno1 = Boolean.parseBoolean(datos[7]);
                    	u = new Cocinero(rol, id, nombre, email, login, password, codigoDesceunto1, estaEnTurno1); 
                    	break;
                    case "CLIENTE":  
                    	String fechaNacimiento = datos[6];
                    	int puntosFidelidad = Integer.parseInt(datos[7]);
                    	String codigoDescuento = datos[8];
                    	u = new Cliente(rol, id, nombre, email, login, password, fechaNacimiento, puntosFidelidad, codigoDescuento); 
                    	break;
                }

                if (u != null) {
                    mapaUsuarios.put(login, u);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de usuarios, iniciando mapa vacío.");
        }
    }
}