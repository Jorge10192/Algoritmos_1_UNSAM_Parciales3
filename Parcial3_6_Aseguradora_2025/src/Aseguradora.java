import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aseguradora {
    //Atributos
    private String nombre;
    private List<Asegurado> listaDeAsegurados = new ArrayList<>();
    private List<Prestador> listaDePrestadores = new ArrayList<>();
    private List<Solicitud> listaDeSolicitudes = new ArrayList<>();

    //Atributo Adicional apra asociar a cada prestador una lista de solicitudes.
    Map<Prestador, List<Solicitud>> solicitudesPorPrestador = new HashMap<>();
    //Atributo Adicional para asignar tiempo de demora a un servicio
    private static final Map<String, Integer> duracionServicios = new HashMap<>();


    //Constructor

    public Aseguradora(String nombre){

        this.nombre = nombre;

        //Asignacion diccionario (Hashmap): Keys: Tipo de Servicio, Contenido: Duracion del trabajo.
        duracionServicios.put("acarreo", 40);
        duracionServicios.put("bateria", 15);
        duracionServicios.put("neumatico", 30);
        duracionServicios.put("mecanica_ligera", 60);

        }



    //Getters
    public List<Prestador> getListaDePrestadores(){
        return listaDePrestadores;
    }
    //Setters

    //---- Metodos Generales para cargar datos en la Aseguradora ----

    //Metodo 1: crear prestador y asignarlo a la lista de prestadores.
    public Prestador registrarPrestador(String nombre, long cuil, String email, String telefono, List<String> tiposDeServicio) {
        Prestador nuevoPrestador = new Prestador(nombre, cuil, email, telefono, tiposDeServicio);
        listaDePrestadores.add(nuevoPrestador);

        // Inicializo la lista vacía de solicitudes para este prestador
        solicitudesPorPrestador.put(nuevoPrestador, new ArrayList<>());
        return nuevoPrestador;
    }

    //Metodo 2: crear asegurado y asignarlo a la lista de asegurados.
    public Asegurado registrarAsegurado(String nombre, int DNI, String email, String telefonoDeContacto, String tipoDePlan, java.time.LocalDateTime fechaDeVencimiento) {
        Asegurado nuevoAsegurado = new Asegurado(nombre, DNI, email, telefonoDeContacto, tipoDePlan, fechaDeVencimiento);
        listaDeAsegurados.add(nuevoAsegurado);
        return nuevoAsegurado;
    }

    //Metodo 3: crear solicitud y asignarlo a la lista de solicitudes.
    public Solicitud solicitar(Asegurado asegurado, String servicioSolicitado){
        //Control del asegurado si puede hacer o no una solicitud.
        // Verificación 1: Límite del plan Tranquilo. (Metodo 6)
        if (!puedeRealizarSolicitudEsteMes(asegurado)) {
            throw new RuntimeException("Plan Tranquilo: Se excedió el límite de solicitudes de este mes.");
        }

        // Verificación 2: Morosidad (más de 90 días desde el último pago). (Metodo 7)
        if (estaMoroso(asegurado)) {
            throw new RuntimeException("Debe regularizar su cuenta. Último pago registrado: " + asegurado.getFechaDeUltimoPago());
        }

        //Crea la solicitud de seguro.
        Solicitud nuevaSolicitud = new Solicitud(asegurado, servicioSolicitado);

        //Creo una lista general de solicitudes, que voy a usar para buscar solicitudes por cliente y no por prestador.
        listaDeSolicitudes.add(nuevaSolicitud);

        //Uso Metodo 4 para asignar un prestador optimo.
        Prestador prestadorOptimo = asignarPrestadorOptimoASolicitud(nuevaSolicitud);
        nuevaSolicitud.setPrestador(prestadorOptimo);

        //Uso Metodo 5 para devolver hora estimada de atencion.
        LocalDateTime horaEstimadaDeAtencion = calcularHoraEstimadaAtencion(nuevaSolicitud);
        nuevaSolicitud.setHoraEstimadaDeAtencion(horaEstimadaDeAtencion);

        return nuevaSolicitud;
    }

    //---- Metodos para conseguir prestador optimo ----
    //Metodo 4: Asignar prestador optimo a una solicitud.
    public Prestador asignarPrestadorOptimoASolicitud(Solicitud solicitud) {
        String servicioSolicitado = solicitud.getServicioSolicitado();

        // Obtengo la lista de prestadores óptimos para ese servicio (usa métodos 4,5 y 6)
        List<Prestador> prestadoresOptimos = obtenerPrestadoresMenorCarga(servicioSolicitado);

        if (prestadoresOptimos.isEmpty()) {
            throw new RuntimeException("No hay prestadores disponibles para el servicio: " + servicioSolicitado);
        }

        // Tomo el primero como el prestador optimo asignado
        Prestador prestadorAsignado = prestadoresOptimos.get(0);

        // Actualizo la solicitud con el prestador y estado
        solicitud.setPrestador(prestadorAsignado);
        solicitud.setEstado(EstadoSolicitud.ASIGNADA);

        // Agrego la solicitud al map de solicitudes por prestador
        if (!solicitudesPorPrestador.containsKey(prestadorAsignado)) {
            solicitudesPorPrestador.put(prestadorAsignado, new ArrayList<>());
        }
        // Agrega la solicitud de a la lista de solicitudes del prestador asignado.
        // Utiliza Metodo 8 para agregar prestador de lujo con prioridad.
        agregarSolicitudAListaDelPrestador(prestadorAsignado, solicitud);

        return prestadorAsignado;
    }

    //--- Metodos auxiliares a Metodo 4 ----

    //Metodo 4.1: Calcular lista de prestadores optimos usando metodos 4.2, 4.3 y 4.4
    public List<Prestador> obtenerPrestadoresMenorCarga(String servicioSolicitado) {
        //Filtro prestadores posibles (Metodo 4)
        List<Prestador> prestadoresConServicio = filtrarPrestadorConServicioSolicitado(servicioSolicitado);

        //Inicializo lista de prestadoresOptimos y cargaMinima.
        List<Prestador> prestadoresOptimos = new ArrayList<>();
        int cargaMinima = Integer.MAX_VALUE; //MaxValue representa un valor muy alto, para inicializar.

        //Determina la cargaMinima y si se cumple lo añade a la lista de prestadores optimos.
        for (Prestador prestador : prestadoresConServicio) {
            int carga = calcularDemoraPendiente(prestador);

            if (carga < cargaMinima) {
                cargaMinima = carga;
                prestadoresOptimos.clear();
                prestadoresOptimos.add(prestador);
            } else if (carga == cargaMinima) {
                prestadoresOptimos.add(prestador);
            }
        }

        return prestadoresOptimos;
    }

    //Metodo 4.2: Identificar prestadores que cuenten con el servicio solicitado por el Asegurado.
    public List<Prestador> filtrarPrestadorConServicioSolicitado(String servicioSolicitado) {
        //Armado de listas con prestadores(general) y lista vacia (para los disponibles)
        List<Prestador> listaPrestadoresAseguradora = getListaDePrestadores();
        List<Prestador> listaPrestadoresDisponibles = new ArrayList<Prestador>();

        // Asigno prestadores
        for(Prestador prestador : listaPrestadoresAseguradora){
            if(prestador.getTiposDeServicio().contains(servicioSolicitado)){
                listaPrestadoresDisponibles.add(prestador);
            }
        }

        return listaPrestadoresDisponibles;
    }

    //Metodo 4.3: Getter de solicitudes del prestador (Utiliza el Map<Prestador,Lista<Solicitud>)
    public List<Solicitud> getSolicitudesDePrestador(Prestador prestador) {
        return solicitudesPorPrestador.get(prestador);
    }

    //Metodo 4.4: Calcular la demora de los servicios que tiene antes asignado el Prestador (Estados: ASIGANDO y EN_PROCSO)
    public int calcularDemoraPendiente(Prestador prestador) {
        //Invoco las solicitudes del prestador e inicializo tiempo de demora en 0.
        int duracionTotal = 0;
        List<Solicitud> solicitudes = getSolicitudesDePrestador(prestador);

        //Si la lista de solicitudes del prestador es vacia, retorno 0 tiempo de demora.
        if (solicitudes == null || solicitudes.isEmpty()) {
            return 0; // No hay solicitudes pendientes, la duración es cero
        }

        //Sumo el tiempo de las solicitudes previas cargadas
        for (Solicitud solicitud : solicitudes) {
            EstadoSolicitud estado = solicitud.getEstado();
            if (estado == EstadoSolicitud.ASIGNADA || estado == EstadoSolicitud.EN_PROCESO) {
                //Utiliza el Map de Servicio : Tiempo Trabajo para obtener el numero (tiempo de demora en el servicio)
                duracionTotal += duracionServicios.get(solicitud.getServicioSolicitado());
            }
        }
        return duracionTotal;
    }

    //--------------------------------------------------

    //Metodo 5: Calcular la Hora de atencion estimada de atención de una solicitud. (El otro atributo de la Solicitud)
    public LocalDateTime calcularHoraEstimadaAtencion(Solicitud solicitud) {
        //Inicializo los atributos de Solicitud necesarios
        LocalDateTime horaBase = LocalDateTime.now();  // Hora actual como base para calcular tiempo de demora.
        Prestador prestador = solicitud.getPrestador();
        List<Solicitud> solicitudesDelPrestador = getSolicitudesDePrestador(prestador); //Metodo 4.3

        //Sumo el tiempo previo de demora hasta la solicitud deseada.
        int minutosAntes = 0;
        for (Solicitud s : solicitudesDelPrestador) {
            if (s.equals(solicitud)) {
                break;
            }

            EstadoSolicitud estado = s.getEstado();
            if (estado == EstadoSolicitud.ASIGNADA || estado == EstadoSolicitud.EN_PROCESO) {
                String servicio = s.getServicioSolicitado();
                Integer duracion = duracionServicios.get(servicio);
                minutosAntes += duracion;

            }
        }

        return horaBase.plusMinutes(minutosAntes);
    }

    //--------------------------------------------------

    //---- Metodos para Verificar si puede el asegurado hacer la solicitud ----

    //Metodo 6: Verificar si el Asegurado puede realizar la solicitud por Plan (Plan Comodo < 2 solicitudes por mes)
    //Este booleano es una condicion inicial al crear la solicitud.
    public boolean puedeRealizarSolicitudEsteMes(Asegurado asegurado) {
        String plan = asegurado.getTipoDePlan().toLowerCase(); // "tranquilo", "comodo", "lujo"

        //Si no tiene límite, siempre puede
        if (plan.equals("comodo") || plan.equals("lujo")) {
            return true;
        }

        // Plan "tranquilo": solo 2 solicitudes por mes
        int solicitudesEsteMes = 0;
        LocalDateTime ahora = LocalDateTime.now();
        //Reviso que el cliente tenga solicitudes activas en el total de solicitudes
        /* Aca se puede armar un filtro mejor
        * Como que devuelva las solicitudes para revisar en los ultimos 2 meses
        * O cargar una lista de solicitudes al cliente y revisar esa lista, pero es mas complejo y largo.
        * Con usar listaDeSolicitudes en general (que es de la aseguradora e incluye a todos los prestadores y clientes)
        * para el ejercicio alcanza.
        *
        * */
        for (Solicitud solicitud : listaDeSolicitudes) {  //Es la lista general de solicitudes, atributo de la Aseguradora
            if (solicitud.getAsegurado().equals(asegurado)) {
                LocalDateTime fecha = solicitud.getFechaHora();
                if (fecha.getMonth().equals(ahora.getMonth()) &&
                        fecha.getYear() == ahora.getYear()) {
                    solicitudesEsteMes++;
                }
            }
        }

        return solicitudesEsteMes < 2;
    }

    //Metodo 7: Control de pago del seguro.
    public boolean estaMoroso(Asegurado asegurado) {
        LocalDateTime fechaDeUltimoPago = asegurado.getFechaDeUltimoPago();
        LocalDateTime ahora = LocalDateTime.now();

        long diasSinPagar = ChronoUnit.DAYS.between(fechaDeUltimoPago, ahora);
        return diasSinPagar > 90;
    }

    //---- Metodos para Agregar a cliente de Lujo con prioridad ----
    //Metodo 8: Agregar cliente de Lujo con prioridad.
    /*
    * El cliente de lujo debe ir lo mas adelante posible, considerando las siguiente excepciones:
    * No debe ir delante de una solicitud si esta ASIGNADA (ya comenzada), porque temporalemente es incorrecto.
    * No debe pasar por delante de otros clientes de lujo que pidieron prestacion antes.
    * */
    private void agregarSolicitudAListaDelPrestador(Prestador prestador, Solicitud solicitud) {
        List<Solicitud> lista = solicitudesPorPrestador.get(prestador);
        String plan = solicitud.getAsegurado().getTipoDePlan().toLowerCase();


        //Agregar al final si el plan no es de lujo.
        if (!plan.equals("lujo")) {
            lista.add(solicitud);
            return;
        }

        // Si el plan es de lujo, buscar la posición correcta (index):
        int index = 0;
        while (index < lista.size()) {
            //Inicializo las variables que necesito revisar de cada solicitud de la lista de solicitudes.
            Solicitud s = lista.get(index);
            String planS = s.getAsegurado().getTipoDePlan().toLowerCase();
            EstadoSolicitud estado = s.getEstado();

            //Se detiene cuando encontramos la primera solicitud activa no-lujo
            if ((estado == EstadoSolicitud.ASIGNADA || estado == EstadoSolicitud.EN_PROCESO)
                    && !planS.equals("lujo")) {
                break;
            }
            //Me paro justo en la posicion de indice siguiente.
            index++;
        }

        // Aniadir en esa posicion la solicitud a la lista de solicitudes.
        lista.add(index, solicitud);
    }


    //---- Metodo para llegada al sitio de atencion ----
    //Metodo 9: LLego al sitio de atencion el Prestador.
    public void llegoAlSitioDeAtencion(Solicitud solicitud) {
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitud.setHoraDeInicioDelServicio(LocalDateTime.now());

        //Mensaje de confirmacion
        System.out.println("La prestación con ID " + solicitud.getID() + " está EN PROCESO.");
        System.out.println("Hora de inicio:" + solicitud.getHoraDeInicioDelServicio() + ".");
    }

    //---- Metodo para marcar atencion realizada ----
    //Metodo 10: Marcar atencion como realizada
    public void atencionRealizada(Solicitud solicitud, int valoracion) {
        //Validacion del puntaje
        if (valoracion < 1 || valoracion > 5) {
            System.out.println("Valoración inválida. Debe estar entre 1 y 5.");
            return;
        }

        //Cambios pedido al finalizar el servicio
        solicitud.setEstado(EstadoSolicitud.REALIZADA);
        solicitud.setHoraDeFinalizacionDelServicio(LocalDateTime.now());

        //Guardar la valoración en el prestador y en el asegurado
        solicitud.getPrestador().agregarValoracion(valoracion);
        solicitud.getAsegurado().agregarValoracion(valoracion);

        //Devuelvo mensaje de confirmacion
        System.out.println("La prestación con ID " + solicitud.getID() + " fue marcada como REALIZADA con una valoración de " + valoracion + ".");
        System.out.println("Hora de finalizacion: " + solicitud.getHoraDeFinalizacionDelServicio());
    }

    //---- Metodo para marcar atencion rechazada ----
    //Metodo 11: Marcar solicitud como rechazada.
    public void solicitudRechazada(Solicitud solicitud, String causa) {
        // Validar causa de rechazo
        List<String> causasValidas = List.of("demora", "no_resuelto", "otros");
        if (!causasValidas.contains(causa.toLowerCase())) {
            System.out.println("Causa inválida. Debe seleccionar un tipo de causa valido (demora, no_resuelto u otros.");
            return;
        }

        //Cambiar estado de la solicitud
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);

        //Guardar la causa del rechazo (tenés que tener un atributo en Solicitud para eso)
        solicitud.setCausaDeRechazo(causa.toLowerCase());

        //Registrar rechazo en el asegurado (para estadísticas)
        solicitud.getAsegurado().incrementarRechazos();

        //Mensaje de confirmación
        System.out.println("La solicitud con ID " + solicitud.getID() + " fue rechazada por causa: " + causa + ".");
    }



    //---- Metodos de impresion ---- (Para todas las impresiones uso la listaDeSolicitudes general)
    //Metodo 12: Imprimir solicitudes registradas.
    public void listarSolicitudes() {
        //Validacion de lista no vacia.
        if (listaDeSolicitudes.isEmpty()) {
            System.out.println("No hay solicitudes registradas.");
            return;
        }

        //Formato de titulos de la tabla.
        System.out.println(String.format("%-5s %-15s %-20s %-15s %-20s %-15s",
                "ID", "Asegurado", "Servicio", "Estado", "Prestador", "Fecha"));

        System.out.println("-------------------------------------------------------------------------------------------");

        //Formato de las filas de la tabla
        for (Solicitud s : listaDeSolicitudes) {
            String nombreAsegurado = s.getAsegurado().getNombre();
            String nombrePrestador = (s.getPrestador() != null) ? s.getPrestador().getNombre() : "No asignado";
            String fecha = (s.getFechaHora() != null) ? s.getFechaHora().toString() : "-";

            System.out.println(String.format("%-5d %-15s %-20s %-15s %-20s %-15s",
                    s.getID(), nombreAsegurado, s.getServicioSolicitado(), s.getEstado(), nombrePrestador, fecha));
        }

        //Tabuladores para tabla
        System.out.println();
        System.out.println();
    }

    //Metodo 13: Imprimir solicitudes Asignadas.
    public void listarSolicitudesEnEspera() {
        boolean hayAsignadas = false;
        //Formato de columnas
        System.out.println(String.format("%-5s %-15s %-20s %-15s %-25s %-20s",
                "ID", "Asegurado", "Servicio", "Prestador", "Hora estimada", "Tiempo de espera"));

        System.out.println("---------------------------------------------------------------------------------------------------------------");
        //Creacion y formateo de filas
        for (Solicitud s : listaDeSolicitudes) {
            if (s.getEstado() == EstadoSolicitud.ASIGNADA) {
                hayAsignadas = true;
                String nombreAsegurado = s.getAsegurado().getNombre();
                String nombrePrestador = s.getPrestador().getNombre();
                LocalDateTime horaEstimada = s.getHoraEstimadaDeAtencion();

                //Utilizo la libreria ChronoUnit para determinar diferencia entre las horas.
                long minutosTotales = ChronoUnit.MINUTES.between(horaEstimada, LocalDateTime.now());
                long horas = minutosTotales / 60;
                long minutos = minutosTotales % 60;

                String tiempoEspera = String.format("%02dh %02dm", horas, minutos);

                //Imprimo la filas
                System.out.println(String.format("%-5d %-15s %-20s %-15s %-25s %-20s",
                        s.getID(), nombreAsegurado, s.getServicioSolicitado(), nombrePrestador,
                        horaEstimada, tiempoEspera));
            }
        }

        if (!hayAsignadas) {
            System.out.println("No hay solicitudes en estado ASIGNADA.");
        }

        //Tabuladores para tabla
        System.out.println();
        System.out.println();
    }

    //Metodo 14: Mostrar asegurados con n solicitudes realizadas
    public void mostrarAseguradosConAlMenosSolicitudes(int numeroDeSolicitudes) {
        //Creo un Map para asociar Asegurados con consultas realizadas (contador).
        Map<Asegurado, Integer> contador = new HashMap<>();

        //Recorro la lista completa de solicitudes y acumulo prestaciones realizadas.
        for (Solicitud solicitud : listaDeSolicitudes) {
            if (solicitud.getEstado() == EstadoSolicitud.REALIZADA) {
                Asegurado asegurado = solicitud.getAsegurado();
                contador.put(asegurado, contador.getOrDefault(asegurado, 0) + 1); //Default devuleve 0 si no tiene solicitudes hechas.
            }
        }

        //Imprime encabezados
        System.out.printf("%-25s %-12s %s%n", "Nombre", "DNI", "Solicitudes Realizadas");
        System.out.println("-------------------------------------------------------");
        //Imprime contenido de las filas
        for (Map.Entry<Asegurado, Integer> entry : contador.entrySet()) {
            Asegurado asegurado = entry.getKey();
            int cantidad = entry.getValue();

            //Condicion para imprimir Asegurado
            if (cantidad >= numeroDeSolicitudes) {
                System.out.printf("%-25s %-12s %5d%n",
                        asegurado.getNombre(),
                        asegurado.getDNI(),
                        cantidad);
            }
        }
        //Tabuladores para tabla
        System.out.println();
        System.out.println();
    }



}
