package administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jornada {
	
	public enum DiaSemana { LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO};
    private Map<DiaSemana, ArrayList<Turno>> turnosPorDia;

    public Jornada() {
        turnosPorDia = new HashMap<>();
        inicializarJornada();
    }
    
    public void inicializarJornada() {
    	for (DiaSemana dia : DiaSemana.values()) {
            turnosPorDia.put(dia, new ArrayList<>());
        }
    }

    public void agregarTurno(DiaSemana dia, Turno turno) {
        turnosPorDia.get(dia).add(turno);
    }

    public ArrayList<Turno> getTurnosDia(DiaSemana dia) {
        return turnosPorDia.get(dia);
    }
    
    public void eliminarTurno(DiaSemana dia, int index) {
    	turnosPorDia.get(dia).remove(index);
    }
    
    public void resetJornada() {
    	turnosPorDia.clear();
    	inicializarJornada();
    }
}