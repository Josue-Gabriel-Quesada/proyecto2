package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
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

            // Crear una instancia de Mapping para realizar mapeos y operaciones CRUD en Oracle
            //MappingOracle oracleMapping = new MappingOracle(oracleConnection);

            // Ejemplo de creación de un trabajador en Oracle
            //String oracleWorkerId = TestOracle.createWorker(oracleMapping, "Josue", 30, "Desarrolador");

            // Ejemplo de recuperación de todos los trabajadores en Oracle
            //TestOracle.retrieveAllWorkers(oracleMapping);

            // Ejemplo de actualización de un trabajador en Oracle
            //TestOracle.updateWorker(oracleMapping, oracleWorkerId, "Josue", 30, "Desarrolador");

            // Ejemplo de eliminación de un trabajador en Oracle
            //TestOracle.deleteWorker(oracleMapping, oracleWorkerId);

            // Conectar a la base de datos MySQL
            Connection mysqlConnection = DatabaseConnection.conectarMySQL();

            // Crear una instancia de Mapping para realizar mapeos y operaciones CRUD en MySQL
            MappingMySQL mysqlMapping = new MappingMySQL(mysqlConnection);

            // Ejemplo de creación de una mascota en MySQL
            TestMySQL.createRecord(mysqlMapping, "Perro", "Firulais", "Labrador", "Carlos");

            // Ejemplo de recuperación de todas las mascotas en MySQL
            TestMySQL.retrieveAllRecords(mysqlMapping);

            // Ejemplo de actualización de una mascota en MySQL
            //TestMySQL.updateRecord(mysqlMapping, "ID_Mascota_A_Modificar", "Gato", "Michi", "Siames", "Laura");

            // Ejemplo de eliminación de una mascota en MySQL
            //TestMySQL.deleteRecord(mysqlMapping, "ID_Mascota_A_Eliminar");

            // Conectar a la base de datos MongoDB
            ///MongoClient mongoClient = MongoClients.create();
            ///MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

            // Crear una instancia de Mapping para realizar mapeos y operaciones CRUD en MongoDB
            ///MappingMongo mongoMapping = new MappingMongo(mongoDatabase);

            // Ejemplo de inserción de una mascota en MongoDB
            ///Mascot mascota = new Mascot("Perro", "Firulais", "Labrador", "Carlos");
            ///mongoMapping.insert(mascota);

            // Ejemplo de recuperación de todas las mascotas en MongoDB
            //System.out.println("Mascotas en MongoDB:");
            ///mongoMapping.selectAll(Mascot.class).forEach(System.out::println);

            // Ejemplo de actualización de una mascota en MongoDB
           /// Mascot mascotaModificada = new Mascot("Gato", "Michi", "Siames", "Laura");
            ///mascotaModificada.setId("ID_Mascota_A_Modificar");
            ///mongoMapping.update(mascotaModificada);

            // Ejemplo de eliminación de una mascota en MongoDB
       //     mongoMapping.delete(Mascot.class, "ID_Mascota_A_Eliminar");
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
        }
    }
}
