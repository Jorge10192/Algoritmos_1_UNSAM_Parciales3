package ejercicio_hospital.entity;

public abstract class Estudio {
    private boolean realizado;

    void realizar(Paciente paciente, Profesional profesional) {
        realizado = true;
        Notificador.notificar(paciente, profesional, this);
    }

    public abstract String getNombre();

    public abstract String getDescripcion();



    @Override
    public String toString() {
        return getNombre() + ": " + getDescripcion() + "(" + realizado + ")";
    }
}
