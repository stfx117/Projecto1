package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import administration.Jornada;
import administration.Jornada.DiaSemana;
import administration.Turno;
import products.JuegoDeMesa;
import products.JuegoDeMesa.Categoria;
import products.JuegoDeMesa.EstadoFisico;
import products.JuegoDeMesa.restriccionEdad;
import services.Mesa;
import services.Prestamo;
import users.Cliente;
import users.Mesero;



public class JornadaTest {
	@Test
	public void testCrearJornada() {
		Jornada j = new Jornada();
		Mesero m1 = new Mesero("MESERO" ,1, "Pepinoto", "peroi@jaslkd.com", "asdjlksd", "kasjd", "231asdasd", true);
		Mesero m2 = new Mesero("MESERO" ,2, "Pepino", "peasdoi@jaslkd.com", "asddsalksd", "kafsdjd", "231afssd", true);
		Turno t1 = new Turno(m1);
		Turno t2 = new Turno(m2);
		j.agregarTurno(DiaSemana.LUNES, t1);
		j.agregarTurno(DiaSemana.DOMINGO, t2);
		ArrayList<Turno> aux1 = j.getTurnosDia(DiaSemana.LUNES);
		assertEquals(aux1, j.getTurnosDia(DiaSemana.LUNES));
		ArrayList<Turno> aux2 = new ArrayList<Turno>();
		j.eliminarTurno(DiaSemana.LUNES, 0);
		assertEquals(aux2, j.getTurnosDia(DiaSemana.LUNES));
		j.agregarTurno(DiaSemana.LUNES, t1);
		j.resetJornada();
		assertEquals(aux2, j.getTurnosDia(DiaSemana.LUNES));
		assertEquals(aux2, j.getTurnosDia(DiaSemana.DOMINGO));
		
	}

}
