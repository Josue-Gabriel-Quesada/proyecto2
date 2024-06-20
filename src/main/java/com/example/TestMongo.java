package com.example;

import java.util.List;

public class TestMongo {

    public static void createDocument(MappingMongo mapping, Mascot mascot) {
        mapping.insert(mascot);
        System.out.println("Objeto creado correctamente (Mongo).");
    }

    public static List<Mascot> retrieveAllDocuments(MappingMongo mapping) {
        List<Mascot> mascots = mapping.selectAll(Mascot.class);
        for (Mascot mascot : mascots) {
            System.out.println("ID: " + mascot.getId_mascot() + ", Tipo: " + mascot.getType() + ", Nombre: " + mascot.getName() + ", Raza: " + mascot.getBreed() + ", Dueño: " + mascot.getOwner());
        }
        return mascots;
    }

    public static void updateDocument(MappingMongo mapping, String id_mascot, String newName, String newBreed, String newOwner) {
        Mascot mascot = mapping.selectById(Mascot.class, id_mascot);
        if (mascot != null) {
            mascot.setName(newName);
            mascot.setBreed(newBreed);
            mascot.setOwner(newOwner);
            mapping.update(mascot);
            System.out.println("Objeto actualizado exitosamente (Mongo).");
        } else {
            System.out.println("No se encontró ninguna Mascota con el ID especificado (Mongo).");
        }
    }

    public static void deleteDocument(MappingMongo mapping, String id_mascot) {
        mapping.delete(Mascot.class, id_mascot);
        System.out.println("Objeto eliminado exitosamente (Mongo).");
    }
}
