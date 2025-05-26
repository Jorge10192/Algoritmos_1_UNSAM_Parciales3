import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class Torneo {
    //Atributos
    //Lista de equipos
    private List<Equipo> listaEquipos = new ArrayList<>();
    //Lista de partidos por fecha
    private Map<LocalDate, List<Partido>> partidosPorFecha = new HashMap<>();

    //Constructor general
    public Torneo(){
    }
    //Cargar Equipos al torneo
    public void cargarEquipo(String nombre, int cantFans){
        Equipo equipo = new Equipo(nombre, cantFans);
        listaEquipos.add(equipo);
    }

    //Cargar partido a la fecha - Version 1: Por objeto equipo
    public void cargarPartido(Equipo EquipoLocal,Equipo EquipoVisita , LocalDate fecha, int golesLocal, int golesVisita){
        Partido partido = new Partido(EquipoLocal, EquipoVisita, fecha, golesLocal, golesVisita);

        // Si no hay lista para esa fecha, se crea una nueva
        partidosPorFecha.putIfAbsent(fecha, new ArrayList<>());

        // Ahora se agrega el partido a la lista de esa fecha
        partidosPorFecha.get(fecha).add(partido);

        }

    //Cargar partido a la fecha - Version 2: Por nombre de equipo
    public void cargarPartido(String nombreLocal, String nombreVisita, LocalDate fecha, int golesLocal, int golesVisita) {
        Equipo equipoLocal = buscarEquipoPorNombre(nombreLocal);
        Equipo equipoVisita = buscarEquipoPorNombre(nombreVisita);

        if (equipoLocal != null && equipoVisita != null) {
            Partido partido = new Partido(equipoLocal, equipoVisita, fecha, golesLocal, golesVisita);

            // Asegurarse de que la fecha tenga su lista
            partidosPorFecha.putIfAbsent(fecha, new ArrayList<>());

            // Agregar el partido
            partidosPorFecha.get(fecha).add(partido);

            // Asignar estadísticas del partido a los equipos
            partido.asignarEstadisticas();

        } else {
            System.out.println("Error: uno o ambos equipos no fueron encontrados.");
        }
    }


    //Mostrar partidos por fecha
    public void mostrarPartidosFecha(LocalDate fecha) {
        List<Partido> partidos = partidosPorFecha.get(fecha);

        if (partidos == null || partidos.isEmpty()) {
            System.out.println("No hay partidos cargados para la fecha: " + fecha);
        } else {
            System.out.println("Partidos del " + fecha + ":");
            for (Partido partido : partidos) {
                partido.resultado();
            }
        }
    }

    //Mostrar tabla con Estadisticas Ordenada -> Algoritmo Orden de Insercion.
    //Metodo para comparar orden de dos equipos (¿El equipo e1 va ordenado por arriba del equipo e2?)
    private boolean vaAntes(Equipo e1, Equipo e2) {
        //Orden por puntos
        if (e1.getPuntuacion() > e2.getPuntuacion()) return true;
        if (e1.getPuntuacion() < e2.getPuntuacion()) return false;
        //Orden por diferencia de goles
        if (e1.getDiferenciaGoles() > e2.getDiferenciaGoles()) return true;
        if (e1.getDiferenciaGoles() < e2.getDiferenciaGoles()) return false;
        //Orden por cantidad de partidos jugados
        return e1.getPartidosJugados() < e2.getPartidosJugados();
    }

    //Metodo para armar lista ordenada de equipos
    public List<Equipo> generarTablaOrdenada(){
        List<Equipo> tablaOrdenada = new ArrayList<>();

        for (Equipo equipo : listaEquipos) {
            // Encuentro la posición donde insertar el equipo actual
            int pos = 0;
            while (pos < tablaOrdenada.size() && !vaAntes(equipo, tablaOrdenada.get(pos))) {
                pos++;
            }
            tablaOrdenada.add(pos, equipo);
        }
        return tablaOrdenada;
    }

    //Imprimir tabla ordenada
    public void mostrarTabla() {
        //Encabezado
        System.out.printf("%-10s | %2s | %2s | %2s | %2s | %2s | %2s | %2s | %3s |\n",
                "Equipo", "Ju", "Pu", "Ga", "Em", "Pe", "GF", "GC", "DG");

        //Creo la copia de la lista ordenada
        List<Equipo> listaOrdenada = generarTablaOrdenada();

        //Imprimo los elementos de la lista ordenada
        for (Equipo equipo : listaOrdenada) {
            System.out.printf("%-10s | %2d | %2d | %2d | %2d | %2d | %2d | %2d | %3d |\n",
                    equipo.getNombre(),
                    equipo.getPartidosJugados(),
                    equipo.getPuntuacion(),
                    equipo.getPartidosGanados(),
                    equipo.getPartidosEmpatados(),
                    equipo.getPartidosPerdidos(),
                    equipo.getGolesAFavor(),
                    equipo.getGolesEnContra(),
                    equipo.getDiferenciaGoles());
        }
    }

    //Ajustes de la clase al TP - Buscar equipo por nombre
    public Equipo buscarEquipoPorNombre(String nombre) {
        for (Equipo e : listaEquipos) {
            if (e.getNombre().equals(nombre)) {
                return e;
            }
        }
        return null; //Se puede lanzar una excepción si no se encuentra
    }


}
