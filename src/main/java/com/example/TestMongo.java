package com.example;

import java.util.List;

public class TestMongo {

    public static void createDocument(MappingMongo mapping, String name, int age, String position) {
        Worker worker = new Worker(name, age, position);
        mapping.insert(worker);
        System.out.println("Objeto creado correctamente(Mongo).");
    }

    public static void retrieveAllDocuments(MappingMongo mapping) {
        List<Worker> workers = mapping.selectAll(Worker.class);
        for (Worker worker : workers) {
            System.out.println("ID: " + worker.getId() + ", Nombre: " + worker.getName() + ", Edad: " + worker.getAge() + ", Puesto: " + worker.getPosition());
        }
    }

    public static void updateDocument(MappingMongo mapping, String id, String newName, int newAge, String newPosition) {
        Worker worker = mapping.selectById(Worker.class, id);
        if (worker != null) {
            worker.setName(newName);
            worker.setAge(newAge);
            worker.setPosition(newPosition);
            mapping.update(worker);
            System.out.println("Objeto actualizado exitosamente(Mongo).");
        } else {
            System.out.println("No se encontró ningún Objeto con el ID especificado(Mongo).");
        }
    }

    public static void deleteDocument(MappingMongo mapping, String id) {
        mapping.delete(Worker.class, id);
        System.out.println("Objeto eliminado exitosamente(Mongo).");
    }
}
