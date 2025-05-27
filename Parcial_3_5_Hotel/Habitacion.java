public class Habitacion {
    private int numeroIdentificadorHab;
    private int capacidadPersonas;

    public Habitacion(int numeroIdentificadorHab, int capacidadPersonas) {
        this.numeroIdentificadorHab = numeroIdentificadorHab;
        this.capacidadPersonas = capacidadPersonas;
    }

    public int getNumeroIdentificadorHab() {
        return numeroIdentificadorHab;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    @Override
    public String toString() {
        return "Habitacion Base " + numeroIdentificadorHab + ", capacidad=" + capacidadPersonas + " pax.";
    }

    //Metodo adicional para mayor claridad en el main
    public String getDescripcion() {
        return toString();
    }
}