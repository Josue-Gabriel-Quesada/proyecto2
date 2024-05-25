/**
 * Clase que realiza el mapeo de objetos a tablas de base de datos.
 * 
 * <p>Esta clase permite mapear objetos Java a tablas de una base de datos relacional, así como realizar operaciones
 * CRUD (Crear, Leer, Actualizar, Eliminar) en dichas tablas.</p>
 * 
 * <p>Para utilizar esta clase, primero se debe establecer una conexión a la base de datos mediante un objeto de tipo
 * Connection. Luego, se pueden realizar operaciones de mapeo y CRUD utilizando este objeto Mapping.</p>
 * 
 * <p>Se recomienda que los nombres de las tablas en la base de datos sean iguales a los nombres de las clases Java
 * en minúsculas.</p>
 * 
 * <p>Esta clase utiliza reflexión para obtener información sobre las clases y campos, por lo que es importante que
 * los nombres de los campos en las clases Java coincidan con los nombres de las columnas en las tablas de la base de datos.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapping {

    private Connection connection;

    public Mapping(Connection connection) {
        this.connection = connection;
    }

    public void mapClassToTable(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String tableName = clazz.getSimpleName().toLowerCase();
            try {
                createTableIfNotExists(tableName, clazz);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            insertData(tableName, clazz, object);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error al mapear la clase a la tabla: " + e.getMessage());
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        String query = "SELECT count(*) FROM user_tables WHERE table_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName.toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private void createTableIfNotExists(String tableName, Class<?> clazz) throws SQLException {
        if (tableExists(tableName)) {
            System.out.println("La tabla " + tableName + " ya existe.");
            return;
        }

        StringBuilder query = new StringBuilder("CREATE TABLE ")
                .append(tableName)
                .append(" (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String dataType = getDataType(field.getType());
            query.append(fieldName).append(" ").append(dataType).append(", ");
        }

        query.delete(query.length() - 2, query.length());
        query.append(")");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            statement.executeUpdate();
            System.out.println("Tabla " + tableName + " creada correctamente.");
        }
    }

    private String getDataType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == double.class || type == Double.class) {
            return "DOUBLE";
        } else if (type == float.class || type == Float.class) {
            return "FLOAT";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        } else {
            return "VARCHAR(255)";
        }
    }

    private void insertData(String tableName, Class<?> clazz, Object object) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder values = new StringBuilder("VALUES (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            query.append(fieldName).append(", ");
            values.append("'").append(fieldValue).append("', ");
        }

        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());

        query.append(") ");
        values.append(")");

        String insertQuery = query.toString() + values.toString();

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.executeUpdate();
            System.out.println("Datos insertados en la tabla " + tableName + " correctamente.");
        }
    }

    public <T> List<T> retrieveFromTable(Class<T> clazz, String tableName) {
        List<T> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        T instance = constructInstance(clazz, resultSet);
                        results.add(instance);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al recuperar objetos de la tabla: " + e.getMessage());
        }
        return results;
    }

    private <T> T constructInstance(Class<T> clazz, ResultSet resultSet) throws SQLException {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = resultSet.getObject(fieldName);
                field.set(instance, fieldValue);
            }

            return instance;
        } catch (Exception e) {
            throw new SQLException("Error al construir instancia de la clase " + clazz.getSimpleName() + ": " + e.getMessage());
        }
    }
    
    public void deleteFromTable(Class<?> clazz, String tableName, String id) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id);
                statement.executeUpdate();
                System.out.println("Registro eliminado de la tabla " + tableName + " exitosamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el registro de la tabla: " + e.getMessage());
        }
    }
}
