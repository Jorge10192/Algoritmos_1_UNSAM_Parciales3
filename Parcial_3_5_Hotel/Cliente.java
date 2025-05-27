public class Cliente {
    private static int contadorClientes = 1;
    private int numeroIdentificadorCliente;
    private String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
        this.numeroIdentificadorCliente = contadorClientes++;
    }

    public int getNumeroIdentificadorCliente() {
        return numeroIdentificadorCliente;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

