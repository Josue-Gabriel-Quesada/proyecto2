package com.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // Establecer la conexión a la base de datos MySQL usando DatabaseConnection
        try (Connection connection = DatabaseConnection.conectarMySQL()) {
            System.out.println("Connected to MySQL database.");

            // Crear una instancia de MappingMySQL 
            MappingMySQL mapping = new MappingMySQL(connection);

            // Crear una nueva casa
            System.out.println("\nCreating a new house in MySQL database:");
            TestMySQL.createHouse(mapping, "Palmar", 11, 350.5);



                  // Eliminar la casa por ID específico (manualmente)
            String manualId = "LTl7L60CCp";
            System.out.println("\nDeleting the house with ID " + manualId + " from MySQL database:");
            TestMySQL.deleteHouse(mapping, manualId);

                   // Cerrar la conexión a la base de datos
            connection.close();
            System.out.println("Disconnected from MySQL database.");

        } catch (SQLException e) {
            System.err.println("Error connecting to MySQL database: " + e.getMessage());
        }
    }
}
