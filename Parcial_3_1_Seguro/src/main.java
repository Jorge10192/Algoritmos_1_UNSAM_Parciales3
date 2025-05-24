import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        //Crear aseguradora
        Aseguradora aseguradora = new Aseguradora();

        // Crear clientes
        Cliente c1 = new Cliente( "Charley", "Daubney", LocalDate.of(1990, 9, 12));
        Cliente c2 = new Cliente( "Grey", "Jarrett", LocalDate.of(1997, 2, 12));
        Cliente c3 = new Cliente( "Sky", "Cornett", LocalDate.of(1989, 1, 22));
        Cliente c4 = new Cliente( "Kelcey", "Belmont", LocalDate.of(1975, 9, 3));
        Cliente c5 = new Cliente( "Terry", "Hodson", LocalDate.of(1958, 9, 8));

        // Crear vehiculos
        Vehiculo v1 = new Vehiculo("ABC123", 10000, 2020);
        Vehiculo v2 = new Vehiculo("XYZ999", 12000, 2022);

        // Generar polizas
        Poliza p1 = aseguradora.asegurarVehiculo(c1, v1, LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 12, 31));
        Poliza p2 = aseguradora.asegurarVehiculo(c1, v2, LocalDate.of(2022, 1, 1),
                LocalDate.of(2023, 12, 31));
        Poliza p3 = aseguradora.asegurarVehiculo(c2, v1, LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31));
        Poliza p4 = aseguradora.asegurarVida(c3, 1000000, LocalDate.of(2017, 1, 1),
                LocalDate.of(2024, 12, 31));
        Poliza p5 = aseguradora.asegurarVida(c4, 2000000, LocalDate.of(2018, 1, 1),
                LocalDate.of(2026, 12, 31));
        Poliza p6 = aseguradora.asegurarVida(c5, 1500000, LocalDate.of(2019, 3, 2),
                LocalDate.of(2020, 3, 2));

        // Transferir poliza
        aseguradora.transferirPoliza(p2, c3);

        // Mostrar polizas
        aseguradora.mostrarPolizas();


    }

}
