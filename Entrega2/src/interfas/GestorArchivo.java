package interfas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestorArchivo
{
	private static final String CARPETA = "archivosTxt";
	
	public void guardarEntidad(GuardadoTxt entidad, String rutaArchivo) throws IOException
	{
		String ruta = CARPETA + File.separator + rutaArchivo;
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true)))
		{
			writer.write(entidad.toLineaTxt());
			writer.newLine();
		} catch(IOException e) 
		{
			System.err.println("Error al escribir en: " + ruta);
			e.printStackTrace();
		}
	}
}