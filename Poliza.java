import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Poliza {

    //Atributos de Poliza
    protected Cliente cliente;
    protected LocalDate inicioVigencia;
    protected LocalDate finVigencia;
    protected float montoAsegurado;
    protected float costoAnual;

    //Constructor general
    public Poliza(Cliente cliente, LocalDate inicioVigencia, LocalDate finVigencia) {
        this.cliente = cliente;
        this.inicioVigencia = inicioVigencia;
        this.finVigencia = finVigencia;
    }

    //Métodos abstractos
    public abstract void calcularMontoAsegurado();
    public abstract void calcularCostoAnual();

    //Metodo para verificar si la póliza está vigente
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(inicioVigencia) && !hoy.isAfter(finVigencia);
    }

    //Metodo para obtener edad del cliente al inico de la poliza
    public int obtenerEdadClienteEnInicio() {
        return cliente.calcularEdadEnFecha(inicioVigencia);
    }

    //Getters
    public Cliente getCliente() {
        return cliente;
    }

    public float getMontoAsegurado() {
        return montoAsegurado;
    }

    public float getCostoAnual() {
        return costoAnual;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public LocalDate getFinVigencia() {
        return finVigencia;
    }


    // Mostrar información general de la póliza
    @Override
    public String toString() {
        return "Cliente: " + cliente +
                "\nEdad al inicio: " + obtenerEdadClienteEnInicio() + " años." +
                "\nMonto asegurado: $" + montoAsegurado +
                "\nCosto anual: $" + costoAnual +
                "\nInicio vigencia: " + inicioVigencia +
                "\nFin vigencia: " + finVigencia +
                "\nVigente: " + (estaVigente() ? "Sí" : "No");
    }

}