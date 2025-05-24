import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aseguradora {
    //Atributo
    private List<Poliza> polizas;

    //Constructor
    public Aseguradora() {
        polizas = new ArrayList<>();
    }

    // 1. Generar póliza de seguro de vida
    public Poliza asegurarVida(Cliente cliente, float montoAsegurado, LocalDate inicio, LocalDate fin) {
        PolizaVida poliza = new PolizaVida(cliente, montoAsegurado, inicio, fin);
        poliza.calcularMontoAsegurado();
        poliza.calcularCostoAnual();
        polizas.add(poliza);
        return poliza;
    }

    // 2. Generar póliza de seguro automotor
    public Poliza asegurarVehiculo(Cliente cliente, Vehiculo vehiculo, LocalDate inicio, LocalDate fin) {
        PolizaVehiculo poliza = new PolizaVehiculo(cliente, vehiculo, inicio, fin);
        poliza.calcularMontoAsegurado();
        poliza.calcularCostoAnual();
        polizas.add(poliza);
        return poliza;
    }

    // 3. Mostrar pólizas agrupadas por estado de vigencia
    public void mostrarPolizas() {
        System.out.println("mostrarPolizas");
        System.out.println("--- Polizas vigentes ---");
        for (Poliza p : polizas) {
            if (p.estaVigente()) {
                imprimirPoliza(p);
            }
        }

        System.out.println("--- Polizas vencidas ---");
        for (Poliza p : polizas) {
            if (!p.estaVigente()) {
                imprimirPoliza(p);
            }
        }
    }

    //Impresion de polizas
    private void imprimirPoliza(Poliza p) {
        System.out.println("-- Poliza --");

        Cliente c = p.getCliente();
        int edad = c.calcularEdadEnFecha(LocalDate.now());
        System.out.println("- Cliente: " + c.getNombre() + " " + c.getApellido() + " (" + edad + ")");

        System.out.println("- Monto asegurado: " + p.getMontoAsegurado());
        System.out.println("- Costo Anual: " + p.getCostoAnual());
        System.out.println("- Vigencia desde: " + p.getInicioVigencia());
        System.out.println("- Vigencia hasta: " + p.getFinVigencia());
        System.out.println("- Esta vigente: " + (p.estaVigente() ? "SI" : "NO"));

        if (p instanceof PolizaVehiculo) {
            Vehiculo v = ((PolizaVehiculo) p).getVehiculo();
            System.out.println("-- Vehiculo --");
            System.out.println("- Dominio: " + v.getDominio());
            System.out.println("- Año: " + v.getAnioFabricacion());
            System.out.println("- Monto compra: " + v.getMontoCompra());
        }
    }

    // 4. Transferir póliza automotor a otro cliente si está vigente
    public void transferirPoliza(Poliza poliza, Cliente nuevoCliente) {
        if (poliza instanceof PolizaVehiculo && poliza.estaVigente()) {
            ((PolizaVehiculo) poliza).setCliente(nuevoCliente);
            System.out.println("Póliza transferida correctamente.");
        } else {
            System.out.println("No se puede transferir la póliza (no es de automotor o no está vigente).");
        }
    }






}
