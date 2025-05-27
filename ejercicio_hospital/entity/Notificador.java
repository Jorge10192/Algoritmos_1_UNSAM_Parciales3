package ejercicio_hospital.entity;

public class Notificador {
    public static void notificar(Paciente paciente , Profesional profesional, Estudio estudio){
        notificarPaciente(paciente, estudio);
        notificarProfesional(profesional, estudio);
    }

    private static void notificarPaciente(Paciente paciente, Estudio estudio){
        System.out.println("Notificando a paciente " + paciente + " sobre " + estudio);
    }
    private static void notificarProfesional(Profesional profesional, Estudio estudio){
        System.out.println("Notificando al profesional " + profesional + " sobre " + estudio);
    }
}
