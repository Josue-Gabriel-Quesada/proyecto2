/**
 * Clase de prueba que demuestra el uso del mapeo de objetos.
 * 
 * <p>Esta clase contiene métodos de prueba que demuestran cómo utilizar el mapeo de objetos para realizar operaciones
 * CRUD en una base de datos.</p>
 * 
 * <p>Se proporcionan ejemplos de creación, recuperación, actualización y eliminación de objetos de tipo Worker en la base de datos.</p>
 * 
 * <p>Para ejecutar estos métodos de prueba, se necesita un objeto de tipo Mapping configurado para conectarse a la base de datos.</p>
 * 
 * <p>Se recomienda ejecutar estos métodos en un entorno de prueba para evitar modificar datos importantes en la base de datos.</p>
 * 
 * <p>Antes de ejecutar estos métodos, asegúrese de que la base de datos esté configurada correctamente y que los nombres de las tablas
 * y los campos coincidan con los nombres utilizados en el código.</p>
 * 
 * <p>Se puede ajustar el código de esta clase según sea necesario para adaptarlo a las necesidades específicas de la aplicación.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

import java.util.List;

public class TestOracle {

    public static String createWorker(MappingOracle mapping, String name, int age, String position) {
        Worker worker = new Worker(name, age, position);
        mapping.insert(worker);
        System.out.println("(Oracle)ID generado para el nuevo trabajador: " + worker.getId());
        return worker.getId(); // Devuelve el ID generado para el trabajador
    }

    public static void retrieveAllWorkers(MappingOracle mapping) {
        List<Worker> workers = mapping.selectAll(Worker.class);
        for (Worker worker : workers) {
            System.out.println("ID: " + worker.getId() + ", Nombre: " + worker.getName() + ", Edad: " + worker.getAge() + ", Puesto: " + worker.getPosition());
        }
    }

    public static void updateWorker(MappingOracle mapping, String id, String newName, int newAge, String newPosition) {
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
        mapping.delete(Worker.class, id);
        System.out.println("Trabajador eliminado exitosamente(Oracle).");
    }
}
