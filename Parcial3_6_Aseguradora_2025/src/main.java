import java.time.LocalDateTime;
import java.util.List;

public class main {
    public static void main(String args[]){

        Aseguradora aseguradora = new Aseguradora("La Pancha");


        // Prestadores
        Prestador rene = aseguradora.registrarPrestador("René", 30376571064L, "rene@mail.com", "1123147898",
                List.of("acarreo", "bateria")); //Modifique la tilde para que compile
        Prestador coco = aseguradora.registrarPrestador("Coco", 30415336566L, "coco@mail.com", "1132143123",
                List.of("acarreo", "batería", "neumatico", "mecanica_ligera"));

        // Asegurados
        Asegurado laura = aseguradora.registrarAsegurado("Laura Gómez", 45234111, "laura@mail.com", "1132939391",
                "tranquilo", LocalDateTime.now());
        Asegurado juan = aseguradora.registrarAsegurado ("Juan Rodríguez", 50234111, "juan@mail.com", "1132939391",
                "comodo", LocalDateTime.now());
        Asegurado pepe = aseguradora.registrarAsegurado ("José Pérez", 50432341, "jose@mail.com", "1145879391", "lujo",
                LocalDateTime.now());
        Asegurado toto = aseguradora.registrarAsegurado ("Toto Sánchez", 47894657, "laura@mail.com", "1156119874",
                "lujo", LocalDateTime.now().minusDays(100)); // moroso


        // Solicitudes
        Solicitud solicitud1 = aseguradora.solicitar(laura, "bateria");
        Solicitud solicitud2 = aseguradora.solicitar(pepe, "mecanica_ligera");
        Solicitud solicitud3 = aseguradora.solicitar(juan, "acarreo");
        Solicitud solicitud4 = aseguradora.solicitar(pepe, "acarreo");
        Solicitud solicitud5 = aseguradora.solicitar(laura, "acarreo");
        Solicitud solicitud6 = aseguradora.solicitar(juan, "bateria");
        Solicitud solicitud7 = aseguradora.solicitar(pepe, "neumatico");
        Solicitud solicitud8 = aseguradora.solicitar(juan, "acarreo");

        //agrego nueva solicitud para poder correr el print format n de 3 solicitudes
        // Solicitud solicitud9 = aseguradora.solicitar(toto, "mecanica_ligera"); //Devuelve la expecion



        try {
            aseguradora.solicitar(laura, "neumatico");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage()); // se excedió el límite de solicitudes de este mes.
        }
        try {
            aseguradora.solicitar(toto, "acarreo");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage()); // Debe regularizar su cuenta con vencimiento:<fecha>
        }



        // Llevar solicitudes a realizado
        aseguradora.llegoAlSitioDeAtencion(solicitud1); aseguradora.atencionRealizada(solicitud1, 5);
        aseguradora.llegoAlSitioDeAtencion(solicitud2); aseguradora.atencionRealizada(solicitud2, 3);
        aseguradora.llegoAlSitioDeAtencion(solicitud3); aseguradora.atencionRealizada(solicitud3, 4);
        aseguradora.llegoAlSitioDeAtencion(solicitud4); aseguradora.atencionRealizada(solicitud4, 1);
        //Agrego las solicitudes generadas que no se invocaron.
        // aseguradora.llegoAlSitioDeAtencion(solicitud5); aseguradora.atencionRealizada(solicitud5, 4); //La dejo asignada sin resolver
        aseguradora.llegoAlSitioDeAtencion(solicitud6); aseguradora.atencionRealizada(solicitud6, 5);
        // aseguradora.llegoAlSitioDeAtencion(solicitud7); aseguradora.atencionRealizada(solicitud7, 3); //La dejo asignada sin resolver
        aseguradora.llegoAlSitioDeAtencion(solicitud8); aseguradora.atencionRealizada(solicitud8, 2);


        // Rechazar
        // aseguradora.solicitudRechazada(solicitud9, "demora");

        // Mostrar todas las solicitudes
        System.out.println("Solicitudes registradas:");
        aseguradora.listarSolicitudes();

        // Mostrar todas las solicitudes en estado asignada y el tiempo que lleva de espera.
        System.out.println("Solicitudes en estado asignada y tiempo que lleva de espera:");
        aseguradora.listarSolicitudesEnEspera();

        // Mostrar los asegurados con al menos 3 solicitudes realizadas.
        System.out.println("\nAseguradores con al menos 3 solicitudes realizadas:");
        aseguradora.mostrarAseguradosConAlMenosSolicitudes(3);


    }
}
