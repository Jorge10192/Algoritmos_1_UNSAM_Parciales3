import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Asegurado {
    //Atributos
    private String nombre;
    private int DNI;
    private String email;
    private String telefonoDeContacto;
    private String tipoDePlan;

    //Atributos adicionales
    private LocalDateTime fechaDeUltimoPago;
    private List<Integer> valoraciones = new ArrayList<>();
    private int cantidadDeRechazos = 0;

    //Constructores
    public Asegurado(String nombre, int DNI, String email, String telefonoDeContacto, String tipoDePlan, LocalDateTime fechaDeUltimoPago) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.email = email;
        this.telefonoDeContacto = telefonoDeContacto;
        this.tipoDePlan = tipoDePlan;
        this.fechaDeUltimoPago = fechaDeUltimoPago;
    }

    //Getters y Setters
    public String getNombre(){return this.nombre;}
    public String getTipoDePlan(){return this.tipoDePlan;}
    public LocalDateTime getFechaDeUltimoPago(){return this.fechaDeUltimoPago;}

    public int getDNI(){return this.DNI;}
    public int getCantidadDeRechazos(){return this.cantidadDeRechazos;}


    //Metodos generales
    //Metodo 1: Agregar valoracion a la lista de valoraciones
    public void agregarValoracion(int valoracion){valoraciones.add(valoracion);}

    //Metodo 2: Incrementar rechazos
    public void incrementarRechazos(){cantidadDeRechazos++;}

    //toString
}
