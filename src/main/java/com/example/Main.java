package com.example;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
    

  try {


            // Conectar a la base de datos Oracle
            Connection oracleConnection = DatabaseConnection.conectarOracleXE();
            // Crear una instancia de MappingOracle para realizar mapeos y operaciones CRUD en Oracle
            MappingOracle oracleMapping = new MappingOracle(oracleConnection);

            // Ejemplo de creación de un trabajador en Oracle
            //TestOracle.createWorker(oracleMapping, "Brandon", BigDecimal.valueOf(30), "Administrador");

            // Recuperar todos los trabajadores
            //TestOracle.retrieveAllWorkers(oracleMapping);
           
              // Buscar un trabajador por ID
              //String oracleWorkerIdToFind = "bnDhboR0qP"; // Aquí defines manualmente el ID del trabajador a buscar
              //Worker foundWorker = TestOracle.findWorkerById(oracleMapping, oracleWorkerIdToFind);
              //if (foundWorker != null) {
                  //System.out.println("Trabajador encontrado: ID: " + foundWorker.getId_worker() + ", Nombre: " + foundWorker.getName() + ", Edad: " + foundWorker.getAge() + ", Puesto: " + foundWorker.getPosition());
              //} else {
                //  System.out.println("No se encontró ningún trabajador con el ID especificado.");
              //}

            // Ejemplo de Actualizar un trabajador en Oracle
            //String oracleWorkerIdToUpdate 
            //TestOracle.updateWorker(oracleMapping, oracleWorkerIdToUpdate, "Nuevo Nombre", BigDecimal.valueOf(32), "Nuevo Puesto");

            // Ejemplo de Eliminar un trabajador en Oracle
            String oracleWorkerIdToDelete = "vThEipWdE7"; 
            TestOracle.deleteWorker(oracleMapping, oracleWorkerIdToDelete);

        } catch (SQLException e) {
            e.printStackTrace();
        }




}

}
