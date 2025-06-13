import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Prestador {
    //Atributos
    private long CUIL;
    private String nombre;
    private String email;
    private String telefonoDeContacto;
    private List<String> tiposDeServicio;

    //Atributos adicionales
    private int tiempoDeDemora;
    private List<String> trabajosEnCola;
    private List<Integer> valoraciones = new ArrayList<>();

    //Constructores
    public Prestador(String nombre,long CUIL,String email,String telefonoDeContacto, List<String> tiposDeServicio){
        this.nombre = nombre;
        this.CUIL = CUIL;
        this.email = email;
        this.telefonoDeContacto = telefonoDeContacto;
        this.tiposDeServicio = tiposDeServicio;
    }

    //Getters y Setters
    public String getNombre(){return this.nombre;}
    public List<String> getTiposDeServicio() {
        return tiposDeServicio;
    }

    //Metodos generales
    //Metodo 1: Agregar valoracion a la lista de valoraciones
    public void agregarValoracion(int valoracion){valoraciones.add(valoracion);}



    //toString
}
