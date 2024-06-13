package com.example;

import java.util.List;

public class TestMySQL {

    public static void createRecord(MappingMySQL mapping, String type, String name, String breed, String owner) {
        Mascot mascot = new Mascot(type, name, breed, owner);
        mapping.insert(mascot);
        System.out.println("Objeto creado correctamente(MySQL).");
    }

    public static void retrieveAllRecords(MappingMySQL mapping) {
        List<Mascot> mascots = mapping.selectAll(Mascot.class);
        for (Mascot mascot : mascots) {
            System.out.println("ID: " + mascot.getId() + ", Tipo: " + mascot.getType() + ", Nombre: " + mascot.getName() + ", Raza: " + mascot.getBreed() + ", Dueño: " + mascot.getOwner());
        }
    }

    public static void retrieveRecordById(MappingMySQL mapping, String id) {
        Mascot mascot = mapping.selectById(Mascot.class, id);
        if (mascot != null) {
            System.out.println("Objeto encontrado:");
            System.out.println("ID: " + mascot.getId() + ", Tipo: " + mascot.getType() + ", Nombre: " + mascot.getName() + ", Raza: " + mascot.getBreed() + ", Dueño: " + mascot.getOwner());
        } else {
            System.out.println("No se encontró ningún registro con el ID especificado(MySQL).");
        }
    }

    public static void updateRecord(MappingMySQL mapping, String id, String newType, String newName, String newBreed, String newOwner) {
        Mascot mascot = mapping.selectById(Mascot.class, id);
        if (mascot != null) {
            mascot.setType(newType);
            mascot.setName(newName);
            mascot.setBreed(newBreed);
            mascot.setOwner(newOwner);
            mapping.update(mascot);
            System.out.println("Registro actualizado exitosamente(MySQL).");
        } else {
            System.out.println("No se encontró ningún registro con el ID especificado(MySQL).");
        }
    }

    public static void deleteRecord(MappingMySQL mapping, String id) {
        mapping.delete(Mascot.class, id);
        System.out.println("Registro eliminado exitosamente(MySQL).");
    }
}
