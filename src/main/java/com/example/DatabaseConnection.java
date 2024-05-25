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

public class DatabaseConnection {
    private static final String USUARIO_ORACLE = "josuee";
    private static final String CONTRASEÑA_ORACLE = "josue123";

    /**
     * Establece una conexión con la base de datos Oracle XE.
     * 
     * @return La conexión establecida.
     * @throws SQLException Si ocurre un error al conectar.
     */
    public static Connection conectarOracleXE() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        return DriverManager.getConnection(url, USUARIO_ORACLE, CONTRASEÑA_ORACLE);
    }
}
