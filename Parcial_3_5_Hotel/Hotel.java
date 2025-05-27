import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Hotel {
    // Atributos
    private String nombre;
    private String direccion;
    private double precioPorNoche;

    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    private int contadorHabitaciones = 1;
    private int contadorReservas = 1;

    // Constructor
    public Hotel(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    // Metodo para definir precio por noche
    public void definirPrecioNoche(double precio) {
        this.precioPorNoche = precio;
    }

    // Metodo para agregar habitación
    public void agregarHabitacion(int capacidad) {
        Habitacion habitacion = new Habitacion(contadorHabitaciones, capacidad);
        habitaciones.add(habitacion);
        contadorHabitaciones++;
    }


    // CONSULTAR DISPONIBILIDAD DE HABITACIONES EN UN RANGO
    public List<Habitacion> consultarDisponibilidad(LocalDate checkin, LocalDate checkout) {
        List<Habitacion> disponibles = new ArrayList<>();

        for (Habitacion hab : habitaciones) {
            boolean ocupada = false;

            for (Reserva res : reservas) {
                if (res.getHabitaciones().contains(hab)) {
                    // Chequeo de superposición de fechas
                    if (!(checkout.isBefore(res.getCheckIn()) || checkin.isAfter(res.getCheckOut()))) {
                        ocupada = true;
                        break;
                    }
                }
            }

            if (!ocupada) {
                disponibles.add(hab);
            }
        }

        return disponibles;

    }


    // RESERVAR HABITACIÓN SEGÚN CANTIDAD DE PERSONAS
    public void reservarHabitacion(Cliente cliente, LocalDate checkin, LocalDate checkout, int cantidadPersonas) {
        // Obtengo habitaciones disponibles en el rango de fechas
        List<Habitacion> disponibles = consultarDisponibilidad(checkin, checkout);

        // Ordeno de menor a mayor capacidad para optimizar selección
        /* Como funciona Comparator,  hay que importarlo.
        *
        * Comparator es la Interfaz, comparingInt es el metodo de la interfaz comparator,
        * Como argumento utiliza el valor int a ordenar (Clase :: Metodo que devuelve int en este caso un get)
        *
        * sort es un metodo de listas.
        * */

        disponibles.sort(Comparator.comparingInt(Habitacion::getCapacidadPersonas));

        // Intento primero encontrar una habitación que alcance sola
        for (Habitacion hab : disponibles) {
            if (hab.getCapacidadPersonas() >= cantidadPersonas) {
                Reserva reserva = new Reserva(List.of(hab), cliente, checkin, checkout, precioPorNoche);
                reservas.add(reserva);
                contadorReservas++;
                System.out.println("Reserva realizada: ID " + reserva.getNumeroReserva() + " con 1 habitación.");
                return;
            }
        }

        // Si no hay una habitación suficiente sola, acumulo habitaciones
        List<Habitacion> seleccionadas = new ArrayList<>();
        int capacidadAcumulada = 0;

        for (Habitacion hab : disponibles) {
            seleccionadas.add(hab);
            capacidadAcumulada += hab.getCapacidadPersonas();

            if (capacidadAcumulada >= cantidadPersonas) {
                Reserva reserva = new Reserva(seleccionadas, cliente, checkin, checkout, precioPorNoche);
                reservas.add(reserva);
                contadorReservas++;
                System.out.println("Reserva realizada: ID " + reserva.getNumeroReserva() + " con " + seleccionadas.size() + " habitaciones.");
                return;
            }
        }

        // Si no se puede cubrir la cantidad con las habitaciones disponibles
        System.out.println("No hay suficientes habitaciones disponibles para " + cantidadPersonas + " personas.");
    }


    // MOSTRAR TODAS LAS RESERVAS DEL HOTEL
    public void mostrarReservasHotel() {
        for (Reserva r : reservas) {
            System.out.println("Reserva " + r.getNumeroReserva() + ": Cliente " + r.getCliente().getNombre()
                    + ", desde " + r.getCheckIn() + " hasta " + r.getCheckOut());
        }
    }


}
