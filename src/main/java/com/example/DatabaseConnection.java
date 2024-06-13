/**
 * Clase para establecer una conexión con la base de datos Oracle XE.
 * 
 * <p>Esta clase proporciona un método estático para conectarse a la base de datos Oracle XE.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DatabaseConnection {
    // Parámetros de conexión para Oracle XE
    private static final String USUARIO_ORACLE = "josuee";
    private static final String CONTRASEÑA_ORACLE = "josue123";
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";

    // Parámetros de conexión para MySQL
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/proyectoo";
    private static final String USUARIO_MYSQL = "root";
    private static final String CONTRASEÑA_MYSQL = "jqa112004p";

    // Parámetros de conexión para MongoDB
    private static final String MONGO_URI = "mongodb://localhost:27017";
    private static final String MONGO_DATABASE_NAME = "proyecto1";

    public static Connection conectarOracleXE() throws SQLException {
        return DriverManager.getConnection(url, USUARIO_ORACLE, CONTRASEÑA_ORACLE);
    }

    public static Connection conectarMySQL() throws SQLException {
        return DriverManager.getConnection(URL_MYSQL, USUARIO_MYSQL, CONTRASEÑA_MYSQL);
    }
    public static MongoDatabase conectarMongoDB() {
        ConnectionString connectionString = new ConnectionString(MONGO_URI);
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(MONGO_DATABASE_NAME);
    }
}
