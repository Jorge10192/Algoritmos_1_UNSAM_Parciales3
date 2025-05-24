import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cliente {
    //Atributos
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    // Constructor
    public Cliente(String nombre, String apellido, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    //getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    //Metodo para calcular edad del cliente a una fecha determinada
    public int calcularEdadEnFecha(LocalDate fecha) {
        return (int) ChronoUnit.YEARS.between(fechaNacimiento, fecha);
    }

    //Metodo para mostrar nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    //Metodo para mostrar cliente
    @Override
    public String toString() {
        return nombre + " " + apellido + " (nacido el " + fechaNacimiento + ")";
    }
}
