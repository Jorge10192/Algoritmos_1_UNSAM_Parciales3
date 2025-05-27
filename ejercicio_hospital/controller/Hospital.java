package ejercicio_hospital.controller;

import ejercicio_hospital.entity.Estudio;
import ejercicio_hospital.entity.Paciente;
import ejercicio_hospital.entity.Profesional;
import ejercicio_hospital.entity.Receta;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Hospital {
    private final String nombre;
    Map<Integer, Profesional> profesionales;
    Map<Integer, Paciente> pacientes;
    List<Receta> recetas;

    public Hospital(String nombre) {
        this.nombre = nombre;
        profesionales = new HashMap<>();
        pacientes = new HashMap<>();
        recetas = new ArrayList<>();
    }

    public Profesional registrarProfesional(String nombre, int matricula) {
        if (profesionales.containsKey(matricula)) {
            throw new RuntimeException("No se puede crear un profesional con una matricula repetida.");
        }
        Profesional prof = new Profesional(matricula, nombre);
        profesionales.put(matricula, prof);
        return prof;
    }

    public Paciente registrarPaciente(String nombre, int dni) {
        if (pacientes.containsKey(dni)) {
            throw new RuntimeException("No se puede crear un paciente con un dni repetido.");
        }
        Paciente paciente = new Paciente(dni, nombre);
        pacientes.put(dni, paciente);
        return paciente;
    }

    public Receta cargarReceta(Profesional profesional, Paciente paciente, Estudio[] estudios) {
        // si existe el profesional
        if (!profesionales.containsKey(profesional.getMatricula())) {
            throw new RuntimeException("Profesional no registrado.");
        }
        // si existe el paciente
        if (!pacientes.containsKey(paciente.getDni())) {
            throw new RuntimeException("Paciente no registrado.");
        }
        // agregar receta a la colección de recetas
        Receta nuevaReceta = new Receta(profesional, paciente, estudios);
        recetas.add(nuevaReceta);
        return nuevaReceta;

    }

    public void procesar(Receta receta) {
        for (Receta rec : recetas) {
            if (rec.getIdReceta() == receta.getIdReceta()) {
                // se puede validar que la receta no este procesada
                rec.procesar();
            }
        }
    }

    public void mostrarRecetas() {
        for (Receta rec : recetas) {
            System.out.println(rec);
        }
    }

    public void mostrarRecetasProcesadas() {
        for (Receta rec : recetas){
            if (rec.isProcesado()){
                System.out.println(rec);
            }
        }
    }

    public void mostrarPacientesConEstudios(int minimoEstudiosRealizados) {
        for (Receta rec : recetas) {
            if (rec.isProcesado() && rec.getEstudios().length >= minimoEstudiosRealizados){
                System.out.println(rec.getPaciente());
            }
        }
    }
}
