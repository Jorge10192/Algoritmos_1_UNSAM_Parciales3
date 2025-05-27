package ejercicio_hospital.entity;

public class Receta {
    private int idReceta;
    private boolean procesado;
    private Estudio[] estudios;
    private Paciente paciente;
    private Profesional profesional;

    public Receta(Profesional profesional, Paciente paciente, Estudio[] estudios) {
        this.paciente = paciente;
        this.estudios = estudios;
        this.profesional = profesional;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public Estudio[] getEstudios() {
        return estudios;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void procesar() {
        procesado = true;
        for (Estudio est : estudios){
            est.realizar(paciente, profesional);
        }
    }

    @Override
    public String toString() {
        String salida = "Receta " + idReceta +
            "\n- Profesional:" + profesional + 
            "\n- Paciente: " + paciente +
            "\n- Estado: " + procesado +
            "\n- Estudios: ";

        for (Estudio est: estudios){
            salida += "\n-" + est;
        }
        return salida;
    }
}
