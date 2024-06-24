package com.example;

import java.math.BigDecimal;
import java.util.List;

public class TestOracle {

    public static String createWorker(MappingOracle mapping, String name, BigDecimal age, String position) {
        Worker worker = new Worker(name, age, position);
        mapping.insert(worker);
        System.out.println("(Oracle)ID generado para el nuevo trabajador: " + worker.getId_worker());
        return worker.getId_worker(); // Devuelve el ID generado para el trabajador
    }

    public static List<Worker> retrieveAllWorkers(MappingOracle mapping) {
        List<Worker> workers = mapping.selectAll(Worker.class);
        if (workers != null) {
            for (Worker worker : workers) {
                System.out.println("ID: " + worker.getId_worker() + ", Nombre: " + worker.getName() + ", Edad: " + worker.getAge() + ", Puesto: " + worker.getPosition());
            }
        } else {
            System.out.println("No se encontraron trabajadores.");
        }
        return workers; // Devolver la lista (puede ser null)
    }

    public static void updateWorker(MappingOracle mapping, String id, String newName, BigDecimal newAge, String newPosition) {
        Worker worker = mapping.selectById(Worker.class, id);
        if (worker != null) {
            worker.setName(newName);
            worker.setAge(newAge);
            worker.setPosition(newPosition);
            mapping.update(worker);
            System.out.println("Trabajador actualizado exitosamente(Oracle).");
        } else {
            System.out.println("No se encontró ningún trabajador con el ID especificado(Oracle).");
        }
    }

    public static void deleteWorker(MappingOracle mapping, String id) {
        // No es necesario verificar si el worker es null antes de eliminarlo
        mapping.delete(Worker.class, id);
        System.out.println("Trabajador eliminado exitosamente(Oracle).");
    }

    public static Worker findWorkerById(MappingOracle mapping, String id) {
        return mapping.selectById(Worker.class, id);
    }
}
