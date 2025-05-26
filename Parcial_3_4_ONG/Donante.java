public class Donante {

    //Atributos
    private String nombre;
    private String apellido;
    private int id;
    //Estadisticas de donaciones para el Donante
    private int cantDonPendientes = 0;
    private int cantDonAceptadas = 0;
    private int cantDonRechazadas = 0;
    private int cantDonTotales = 0;

    //Constructor general
    public Donante(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    }

    //getters
    public String getNombre(){return this.nombre;}

    public String getApellido(){return this.apellido;}

    public int getId(){return this.id;}

    public int getCantDonPendientes(){return this.cantDonPendientes;}
    public int getCantDonAceptadas(){return this.cantDonAceptadas;}
    public int getCantDonRechazadas(){return this.cantDonRechazadas;}
    public int getCantDonTotales(){return this.cantDonTotales;}

    //Asignar ID
    public void asignarID(int numero){this.id = numero;}

    //Editar Estadisticas
    public void incrementarCantDonPendientes() {this.cantDonPendientes++;}
    public void disminuirCantDonPendientes() {this.cantDonPendientes--;}

    public void incrementarCantDonAceptadas() {this.cantDonAceptadas++;}
    public void disminuirCantDonAceptadas() {this.cantDonAceptadas--;}

    public void incrementarCantDonRechazadas() {this.cantDonRechazadas++;}
    public void disminuirCantDonRechazadas() {this.cantDonRechazadas--;}

    public void calcularCantDonTotales(){
        this.cantDonTotales = cantDonPendientes + cantDonAceptadas + cantDonRechazadas;
    }

}
