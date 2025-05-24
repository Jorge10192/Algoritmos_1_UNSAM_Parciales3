import java.time.LocalDate;

public class PolizaVehiculo extends Poliza {
    //Atributo
    private Vehiculo vehiculo;

    //Constructor General
    public PolizaVehiculo(Cliente cliente, Vehiculo vehiculo, LocalDate inicioVigencia, LocalDate finVigencia) {
        super(cliente, inicioVigencia, finVigencia);
        this.vehiculo = vehiculo;
        calcularMontoAsegurado();
        calcularCostoAnual();
    }

    //setter para cambio de cliente
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    //Metodos heredados
    @Override
    public void calcularMontoAsegurado() {
        // El monto asegurado es la cotización del vehículo al inicio de la póliza
        montoAsegurado = vehiculo.cotizar(inicioVigencia);
    }

    @Override
    public void calcularCostoAnual() {
        int edad = cliente.calcularEdadEnFecha(inicioVigencia);
        if (edad < 30) {
            costoAnual = montoAsegurado * 0.20f;
        } else {
            costoAnual = montoAsegurado * 0.10f;
        }
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nVehículo: " + vehiculo.toString();
    }
}