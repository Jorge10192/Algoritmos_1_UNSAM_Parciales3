import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ong {

    //Atributos
    private String nombreOng;
    private List<Donante> listaDonantes = new ArrayList<Donante>();
    private HashMap<LocalDate, Donacion> listaDonaciones; //Supongo 1 donacion por persona por dia
    private Map<LocalDate, List<Donacion>> donacionesPorFecha = new HashMap<>();


    //Constructor general
    public Ong(String nombreOng){
        this.nombreOng = nombreOng;
    }


    //Metodos
    //Asignar identificadores unicos a donadores y donaciones
    //Contadores de Ids
    private int proximoIdDonante = 1;
    private int proximoIdDonacion = 1;


    public void asignarIdDonante(Donante donante){
        donante.asignarID(proximoIdDonante);
        proximoIdDonante++;
    }

    public void asignarIdDonacion(Donacion donacion){
        //Armar Id de donacion con Id Donante y numero de donacion
        int id1 = donacion.getDonante().getId();
        String idDonacion = id1 + "-" + proximoIdDonacion;
        //Asignar idDonancion
        donacion.asignarID(idDonacion);
        //Aumentar el valor de la donacion
        proximoIdDonacion++;
    }

    //Acciones de la ONG
    //Registrar Donante a ONG
    public Donante registrarDonante(String nombre, String apellido){
        Donante donante = new Donante(nombre, apellido);
        asignarIdDonante(donante);
        listaDonantes.add(donante);

        return donante;
    }

    //Cargar Donacion a ONG
    public Donacion cargarDonacion(Donante donante, LocalDate fecha, int importe){
        Donacion donacion = new Donacion(donante, fecha, importe);
        asignarIdDonacion(donacion);

        //Cargar la Donacion al Map<fecha,donacion>
        //Verifica si ya existe una lista de donaciones para esa fecha.
        List<Donacion> donacionesEnFecha = donacionesPorFecha.get(fecha);
        //Si no existe, se crea una nueva lista para donaciones en esa fecha.
        if (donacionesEnFecha == null) {
            donacionesEnFecha = new ArrayList<>();
            donacionesPorFecha.put(fecha, donacionesEnFecha);
        }

        donacionesEnFecha.add(donacion);

        return donacion;
    }

    //Registro de Donadores
    //Ordenar a los donadores alfabeticamente
    private void insertarOrdenado(List<Donante> lista, Donante nuevo) {
        int i = 0;
        while (i < lista.size()) {
            Donante actual = lista.get(i);
            //Comparacion: primero apellido, luego nombre
            int comparacionApellido = nuevo.getApellido().compareToIgnoreCase(actual.getApellido());
            if (comparacionApellido < 0){break;}

            int comparacionNombre = nuevo.getNombre().compareToIgnoreCase(actual.getNombre());
            if (comparacionNombre < 0){break;}
            //Si no va antes apellido o nombre, comparar con el sigueinte en la lista.
            i++;
        }
        lista.add(i, nuevo);
    }

    public List<Donante> ordenarLista(List<Donante> listaDonantes){
        List<Donante> donantesOrdenados = new ArrayList<>();

        //Insertar donadores de forma ordenada
        for (Donante d : listaDonantes) {
            insertarOrdenado(donantesOrdenados, d);
        }

        return donantesOrdenados;
    }

    //Registro de donantes.
    public void mostrarDonantes(){
        //Encabezado del registro
        System.out.println("Listado de donantes de " + this.nombreOng + ":\n");

        System.out.printf("%-15s | %-12s | %-4s | %-10s | %-10s | %-11s | %-7s|\n",
                "Apellido", "Nombre", "ID", "Pendientes", "Aceptadas", "Rechazadas", "Totales");

        System.out.println("----------------------------------------------------------------------------");

        //Ordeno la lista de Donantes
        List<Donante> listaDonantesOrdenados = ordenarLista(listaDonantes);

        //Armar filas con donantes
        for (Donante d : listaDonantesOrdenados) {
            d.calcularCantDonTotales(); // Asegurate de actualizar el total si no lo hiciste en cada cambio

            System.out.printf("%-15s | %-12s | %-4d | %-10d | %-10d | %-11d | %-7d|\n",
                    d.getApellido(),
                    d.getNombre(),
                    d.getId(),
                    d.getCantDonPendientes(),
                    d.getCantDonAceptadas(),
                    d.getCantDonRechazadas(),
                    d.getCantDonTotales()
            );
        }
    }


    //Lista de donaciones ordenadas por fecha.
    public void mostrarDonaciones(){
        //Armo un TreeMap para usar las claves de fechas ordenadas.
        Map<LocalDate, List<Donacion>> fechasOrdenadas = new TreeMap<>(donacionesPorFecha);

        //Encabezado
        System.out.println("Listado de donaciones de " + this.nombreOng + ":\n");

        //Mostrar las donaciones por fecha
        for (Map.Entry<LocalDate, List<Donacion>> entrada : fechasOrdenadas.entrySet()) {
            List<Donacion> lista = entrada.getValue();

            for (Donacion d : lista) {
                System.out.println("Donacion " + d.getIdDonacion());
                System.out.println("- Donante: (" + d.getDonante().getId() + ") "
                        + d.getDonante().getApellido() + ", "
                        + d.getDonante().getNombre());
                System.out.println("- Fecha: " + d.getFecha());
                System.out.println("- Estado: " + d.getEstado());
                System.out.println("- Monto: " + d.getImporte());
                System.out.println(); // Línea en blanco para separar donaciones
            }
        }
    }

    //Mostrar donaciones desde 0 hasta una fecha especifica
    public void mostrarResultadoFecha(LocalDate fechaLimite) {
        //Inicializo estadisticas para donaciones desde 0 hasta fecha pedida.
        int cantCobradas = 0;
        int cantRechazadas = 0;
        int cantPendientes = 0;
        double sumaMontos = 0;
        double montoMaximo = Double.MIN_VALUE;
        double montoMinimo = Double.MAX_VALUE;
        int totalDonaciones = 0;

        //Armo un TreeMap para ordernar las fechas coronologicamente (claves)
        TreeMap<LocalDate, List<Donacion>> donacionesOrdenadas = new TreeMap<>(donacionesPorFecha);

        //Recorro las fechas ordenadas, hasta la fecha limite.
        for (Map.Entry<LocalDate, List<Donacion>> fechaEntrada : donacionesOrdenadas.entrySet()) {
            LocalDate fecha = fechaEntrada.getKey();
            if (fecha.isAfter(fechaLimite)) {
                // Si la fecha es posterior a la límite, cortamos el recorrido
                break;
            }
            //Recorro las donaciones dentro de una fecha (llamo las donaciones con Key.obtenerValores)
            List<Donacion> donaciones = fechaEntrada.getValue();
            for (Donacion d : donaciones) {
                totalDonaciones++;
                switch (d.getEstado()) {
                    case COBRADA -> {
                        cantCobradas++;
                        sumaMontos += d.getImporte();

                        if (d.getImporte() > montoMaximo) {
                            montoMaximo = d.getImporte();
                        }
                        if (d.getImporte() < montoMinimo) {
                            montoMinimo = d.getImporte();
                        }
                    }
                    case RECHAZADA -> cantRechazadas++;
                    case PENDIENTE -> cantPendientes++;
                }
            }
        }
        //Calculo el promedio de donacinoes
        double montoMedioDonado = totalDonaciones > 0 ? sumaMontos / cantCobradas : 0;

        //Imprimo el resultado de las estadisticas hasta esa fecha.
        System.out.println("Estado de Resultado de " + nombreOng + " hasta la fecha: "+ fechaLimite);
        System.out.println("- Cobradas: " + cantCobradas);
        System.out.println("- Rechazadas: " + cantRechazadas);
        System.out.println("- Pendientes: " + cantPendientes);
        System.out.println("- Monto Total: $ " + sumaMontos);
        System.out.println("- Monto máximo: $ " + montoMaximo);
        System.out.println("- Monto mínimo: $ " + (montoMinimo == Double.MAX_VALUE ? 0 : montoMinimo));
        System.out.println("- Monto medio: $ " + String.format("%.2f", montoMedioDonado));

    }


}
