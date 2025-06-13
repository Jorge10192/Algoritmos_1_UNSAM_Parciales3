import java.time.LocalDateTime;
import java.util.List;

public class Solicitud {
    //Atributos
    private Asegurado asegurado;
    private Prestador prestadorAsignado;
    private String servicioSolicitado; //Uno de los servicios que brinda el prestador
    private LocalDateTime horaEstimadaDeAtencion;

    //Atributos adicionales
    private EstadoSolicitud estado;
    private static int contadorID = 0;
    private final int ID;
    private LocalDateTime fechaHora; //Marca el inicio de la solicitud
    private LocalDateTime horaDeInicioDelServicio;
    private LocalDateTime horaDeFinalizacionDelServicio;
    private String causaDeRechazo;

    //Constructores
    public Solicitud(Asegurado asegurado, String servicioSolicitado) {
        this.asegurado = asegurado;
        this.servicioSolicitado = servicioSolicitado;
        this.estado = EstadoSolicitud.PENDIENTE; //Enunciado pide inicializarlo como PENDIENTE.
        this.ID = contadorID++; // Asigna ID único y lo incrementa para la próxima
        this.fechaHora = LocalDateTime.now();
    }

    //Getters
    public Prestador getPrestador(){return this.prestadorAsignado;}
    public Asegurado getAsegurado(){return this.asegurado;}
    public EstadoSolicitud getEstado(){return this.estado;}
    public String getServicioSolicitado(){
        return this.servicioSolicitado;
    }
    public LocalDateTime getFechaHora(){return this.fechaHora;}
    public LocalDateTime getHoraEstimadaDeAtencion(){return this.horaEstimadaDeAtencion;}
    public LocalDateTime getHoraDeInicioDelServicio(){return horaDeInicioDelServicio;}
    public LocalDateTime getHoraDeFinalizacionDelServicio(){return horaDeFinalizacionDelServicio;}

    public int getID() {return ID;}

    public String getCausaDeRechazo(){return this.causaDeRechazo;}

    //Setters (Para prestador y horaEstimadaDeAtencion serian mas constructores que setters porque no pueden inicializarse)
    public void setPrestador(Prestador prestador) {this.prestadorAsignado = prestador;}

    public void setEstado(EstadoSolicitud nuevoEstado) {this.estado = nuevoEstado;}

    public void setHoraEstimadaDeAtencion(LocalDateTime horaEstimadaDeAtencion){
        this.horaEstimadaDeAtencion = horaEstimadaDeAtencion;
    }

    public void setHoraDeInicioDelServicio(LocalDateTime horaDeInicioDelServicio){
        this.horaDeInicioDelServicio = horaDeInicioDelServicio;
    }

    public void setHoraDeFinalizacionDelServicio(LocalDateTime horaDeFinalizacionDelServicio){
        this.horaDeFinalizacionDelServicio = horaDeFinalizacionDelServicio;
    }

    public void setCausaDeRechazo(String causaDeRechazo){
        this.causaDeRechazo = causaDeRechazo;
    }

    //Metodos generales



    //toString
}
