import java.time.LocalDate;

public class Donacion {

    //Atributos
    private Donante donante;
    private LocalDate fecha;
    private double importe;
    private String id;
    private EstadoDonacion estado = EstadoDonacion.PENDIENTE;

    //Constructor
    public Donacion(Donante donante, LocalDate fecha, double importe){
        this.donante = donante;
        this.fecha = fecha;
        this.importe = importe;
        donante.incrementarCantDonPendientes();
    }

    //getters
    public Donante getDonante(){return donante;}

    public LocalDate getFecha(){return fecha;}

    public double getImporte(){return importe;}

    public String getIdDonacion(){return id;}

    public EstadoDonacion getEstado(){return estado;}

    //setters

    public void setCobrada(){
        this.estado = EstadoDonacion.COBRADA;
        this.donante.incrementarCantDonAceptadas();
        this.donante.disminuirCantDonPendientes();
    }

    public void setRechazada() {
        this.estado = EstadoDonacion.RECHAZADA;
        this.donante.incrementarCantDonRechazadas();
        this.donante.disminuirCantDonPendientes();
    }

    //Asignar ID
    public void asignarID(String numero){
        this.id = numero;
    }

}
