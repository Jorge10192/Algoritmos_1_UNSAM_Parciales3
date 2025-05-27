package ejercicio_hospital.entity;

public class EstudioLaboratorio extends Estudio {
    private int cantidad;

    public EstudioLaboratorio(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String getNombre() {
        return "Análisis de Laboratorio";
    }

    @Override
    public String getDescripcion() {
        return String.format(
                "Un análisis de Laboratorio con una cantidad %d de variables."
                , cantidad);
    }
}
