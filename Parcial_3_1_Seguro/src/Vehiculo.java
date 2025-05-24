import java.time.LocalDate;

public class Vehiculo {
    //Atributos
    private String dominio;         // Patente
    private int anioFabricacion;
    private float montoCompra;
    //Constructor principal
    public Vehiculo(String dominio, int anioFabricacion, float montoCompra) {
        this.dominio = dominio;
        this.anioFabricacion = anioFabricacion;
        this.montoCompra = montoCompra;
    }

    //getters
    public String getDominio() {
        return dominio;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public float getMontoCompra() {
        return montoCompra;
    }


    /* Encapsulamiento (Donde viven los datos)
    *
    * Si bien Antiguedad y Cotizacion, se utilizan al generar la poliza, son datos que
    * puede responder la clase vehiculo cuando se llama a la misma.
    * ¿Cual es la antiguedad del vehiculo? LLama al año de fabricacion
    * ¿Cual es la cotizacion? Llama a antiguedad y monto de compra.
    *
    * */
    // Antigüedad del vehículo al inicio de la póliza
    public int calcularAntiguedad(LocalDate fechaInicioPoliza) {
        return fechaInicioPoliza.getYear() - anioFabricacion;
    }

    // Cotización al inicio de la póliza
    public float cotizar(LocalDate fechaInicioPoliza) {
        int antiguedad = calcularAntiguedad(fechaInicioPoliza);
        float depreciacion = 0.05f * antiguedad;  //forzar tipo de double a float
        float cotizacion = montoCompra * (1 - depreciacion);

        // No puede ser menor que 0
        return Math.max(0, cotizacion);
    }


    //Metodo de impresion
    @Override
    public String toString() {
        return "Dominio: " + dominio + ", Año: " + anioFabricacion + ", Monto de compra: $" + montoCompra;
    }
}
