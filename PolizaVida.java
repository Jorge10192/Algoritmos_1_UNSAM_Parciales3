import java.time.LocalDate;

public class PolizaVida extends Poliza {

    public PolizaVida(Cliente cliente, float montoAsegurado, LocalDate inicioVigencia, LocalDate finVigencia){
        super(cliente, inicioVigencia, finVigencia);
        this.montoAsegurado = montoAsegurado;
        calcularCostoAnual();
    }

    @Override
    public void calcularMontoAsegurado() {
        // El cliente define el monto, ya está dado en el constructor
    }

    @Override
    public void calcularCostoAnual() {
        int edad = cliente.calcularEdadEnFecha(inicioVigencia);
        if (edad < 35) {
            costoAnual = montoAsegurado * 0.05f;
        } else {
            costoAnual = montoAsegurado * 0.10f;
        }
    }

    @Override
    public String toString() {
        return "PÓLIZA DE VIDA\n" +
                cliente +
                "\nMonto asegurado: $" + montoAsegurado +
                "\nCosto anual: $" + costoAnual +
                "\nVigencia: " + inicioVigencia + " a " + finVigencia +
                "\nVigente: " + (estaVigente() ? "Sí" : "No");
    }

}
