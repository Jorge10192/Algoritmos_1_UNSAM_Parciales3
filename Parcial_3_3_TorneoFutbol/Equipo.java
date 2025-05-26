import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Equipo {

    //Atributos generales
    private final String nombre;
    private int cantFans;

    //Atributos para estadisticas
    private int puntuacion = 0;
    private int partidosJugados = 0;
    private int partidosGanados = 0;
    private int partidosEmpatados = 0;
    private int partidosPerdidos = 0;
    private int golesAFavor = 0;
    private int golesEnContra = 0;
    private int diferenciaDeGoles = 0;

    //Constructor primario
    public Equipo(String nombre, Integer cantFans) {
        this.nombre = nombre;
        this.cantFans = cantFans;
    }

    //Asignar estadisticas
    public void actualizarEstadisticas(int golesAFavor, int golesEnContra) {
        this.partidosJugados++;
        this.golesAFavor += golesAFavor;
        this.golesEnContra += golesEnContra;

        if (golesAFavor > golesEnContra) {
            this.partidosGanados++;
            this.puntuacion += 3;
        } else if (golesAFavor == golesEnContra) {
            this.partidosEmpatados++;
            this.puntuacion += 1;
        } else {
            this.partidosPerdidos++;
        }
    }


    //getters
    public String getNombre(){
        return nombre;
    }

    public int getCantFans(){
        return cantFans;
    }

    public int getPuntuacion(){
        return puntuacion;
    }

    public int getPartidosJugados(){
        return partidosJugados;
    }

    public int getPartidosGanados(){
        return partidosGanados;
    }

    public int getPartidosEmpatados(){
        return partidosEmpatados;
    }

    public int getPartidosPerdidos(){
        return partidosPerdidos;
    }

    public int getGolesAFavor(){
        return golesAFavor;
    }

    public int getGolesEnContra(){
        return golesEnContra;
    }

    public int getDiferenciaGoles(){
        return (golesAFavor - golesEnContra);
    }


}
