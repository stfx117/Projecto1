package administration;

public class Caja {

    private double dineroDisponible;

    public Caja(double dineroDisponible) {
        this.dineroDisponible = dineroDisponible;
    }

    public void recibirDinero(double monto) {
        if (monto > 0) {
            this.dineroDisponible = this.dineroDisponible + monto;
        }
    }

    public double darDinero(double monto) {
        if (monto > this.dineroDisponible) {
            throw new IllegalArgumentException("No hay suficiente dinero en caja");
        }
        this.dineroDisponible = this.dineroDisponible - monto;
        return monto;
    }

    public double getDineroDisponible() {
        return dineroDisponible;
    }
    
    public void setDineroDisponible(double dineroDisponible) {
    	this.dineroDisponible = dineroDisponible;
    }
}