package ejercicio_hospital.boundary;

import ejercicio_hospital.controller.*;
import ejercicio_hospital.entity.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Pura Salud");
        Profesional juana = hospital.registrarProfesional("Juana", 12345);
        Profesional ana = hospital.registrarProfesional("Ana", 11234);
        Profesional maria = hospital.registrarProfesional("Maria", 54321);

        Paciente pedro = hospital.registrarPaciente("Pedro", 35234111);
        Paciente tomas = hospital.registrarPaciente("Tomas", 34942999);
        Paciente juan = hospital.registrarPaciente("Juan", 32912000);
        Receta receta1 = hospital.cargarReceta(juana, pedro, new Estudio[] {
                new EstudioRX("columna"),
                new EstudioRX("torax")
        });
        Receta receta2 = hospital.cargarReceta(juana, tomas, new Estudio[] {
                new EstudioRX("abdomen"),
                new EstudioRX("torax")
        });
        Receta receta3 = hospital.cargarReceta(ana, juan, new Estudio[] {
                new EstudioRX("abdomen"),
                new EstudioLaboratorio(5)
        });
        Receta receta4 = hospital.cargarReceta(ana, pedro, new Estudio[] {
                new EstudioLaboratorio(15)
        });
        Receta receta5 = hospital.cargarReceta(maria, juan, new Estudio[] {
                new EstudioRX("columna"),
                new EstudioRX("abdomen"),
                new EstudioLaboratorio(10)
        });
        hospital.procesar(receta1);
        hospital.procesar(receta2);
        hospital.procesar(receta3);
        hospital.procesar(receta4);

        hospital.mostrarRecetas();
    // Mostrar recetas procesadas
        hospital.mostrarRecetasProcesadas();
    // Mostrar pacientes con al menos 2 estudios realizados
        hospital.mostrarPacientesConEstudios(3);
    }
}
