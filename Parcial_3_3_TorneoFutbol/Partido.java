import java.time.LocalDate;

public class Partido{

    //Atributos
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private Ganador ganador;
    private LocalDate fecha;

    //Constructor basico
    public Partido(Equipo equipoLocal, Equipo equipoVisitante,LocalDate fecha, int golesLocal, int golesVisitante){
            this.equipoLocal = equipoLocal;
            this.equipoVisitante = equipoVisitante;
            this.golesLocal = golesLocal;
            this.golesVisitante = golesVisitante;
            this.fecha = fecha;
    }

    //Metodos
    //Asignar estadisticas de partido

    public void asignarEstadisticas(){
        //Estdisticas de Equipo local
        equipoLocal.actualizarEstadisticas(golesLocal, golesVisitante);
        //Estadisticas de Equipo visitante
        equipoVisitante.actualizarEstadisticas(golesVisitante, golesLocal);
    }


    //Generar Resultado de Partido
    public String resultado() {
        return "Resultado Final: " +
                equipoLocal.getNombre() + " " + golesLocal + " - " +
                golesVisitante + " " + equipoVisitante.getNombre();
    }


}