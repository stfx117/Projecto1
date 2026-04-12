package administration;

import java.util.List;

public class Jornada {

    private List<Turno> turnos;

    public Jornada(List<Turnos> turnos) {
        this.turnos = turnos;
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public void quitarTurno(Turno turno) {
        turnos.remove(turno);
    }

    public List<Turno> getTurnos() {
        return turnos;
    }
}