/**
 * Clase principal que demuestra el uso de la conexión a la base de datos y el mapeo de objetos.
 * 
 * <p>Esta clase contiene el método principal para iniciar la aplicación, donde se muestra un ejemplo
 * de cómo utilizar la conexión a la base de datos y realizar operaciones CRUD mediante un objeto de mapeo.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try {
            // Conectar a la base de datos Oracle
            Connection oracleConnection = DatabaseConnection.conectarOracleXE();

            // Crear una instancia de Mapping para realizar mapeos y operaciones CRUD
            Mapping mapping = new Mapping(oracleConnection);

            // Ejemplo de creación de un trabajador
            String id = Test.createWorker(mapping, "Juan", 30, "Programador");

            // Ejemplo de recuperación de todos los trabajadores
            Test.retrieveAllWorkers(mapping);

            // Ejemplo de actualización de un trabajador
            Test.updateWorker(mapping, id, "NuevoNombre", 25, "Analista");

            // Ejemplo de eliminación de un trabajador
            Test.deleteWorker(mapping, id);
            
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    }
}
