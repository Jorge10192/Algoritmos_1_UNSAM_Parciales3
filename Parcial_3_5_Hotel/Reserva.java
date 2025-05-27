import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Reserva {
    private static int contadorReservas = 1;
    private int numeroReserva;
    private List<Habitacion> habitaciones;  // ahora es lista
    private Cliente cliente;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int cantidadNoches;
    private double monto;

    // Constructor recibe lista de habitaciones
    public Reserva(List<Habitacion> habitaciones, Cliente cliente, LocalDate checkIn, LocalDate checkOut, double precioPorNoche) {
        this.numeroReserva = contadorReservas++;
        this.habitaciones = habitaciones;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cantidadNoches = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        this.monto = precioPorNoche * cantidadNoches * habitaciones.size(); // monto por todas las habitaciones
    }

    // Getters
    public int getNumeroReserva() {
        return numeroReserva;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getCantidadNoches() {
        return cantidadNoches;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("** Reserva ").append(numeroReserva).append("\n");
        for (Habitacion hab : habitaciones) {
            sb.append("- Habitacion: ").append(hab).append("\n");
        }
        sb.append("- Cliente: ").append(cliente.getNombre()).append("\n");
        sb.append("- Checkin: ").append(checkIn).append("\n");
        sb.append("- Checkout: ").append(checkOut).append("\n");
        sb.append("- Cantidad de noches: ").append(cantidadNoches).append("\n");
        sb.append("- Monto: $").append(monto).append("\n");
        return sb.toString();
    }
}
