package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MappingOracle {

    private Connection connection;

    public MappingOracle(Connection connection) {
        this.connection = connection;
    }

    // CRUD INSERT ==========================================================================
    public void insert(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String tableName = clazz.getSimpleName().toLowerCase(); // Assuming the table name is the same as the class name in lowercase
            try {
                // Create table if it doesn't exist
                createTableIfNotExists(tableName, clazz);
            } catch (SQLException e) {
                System.err.println("Error creating table: " + e.getMessage());
            }

            // Insert data into the table
            insertData(tableName, clazz, object);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error mapping class to table: " + e.getMessage());
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
            System.out.println("Table " + tableName + " already exists.");
            return;
        }

        StringBuilder query = new StringBuilder("CREATE TABLE ")
                .append(tableName)
                .append(" (");

        Field[] fields = clazz.getDeclaredFields();
        boolean hasPrimaryKey = false;
        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields
            String fieldName = field.getName();
            String dataType = getDataType(field.getType());
            query.append(fieldName).append(" ").append(dataType);
            // Check if it is the primary key
            if (fieldName.equals("id_worker")) {
                query.append(" PRIMARY KEY");
                hasPrimaryKey = true;
            }
            query.append(", ");
        }

        // Remove the extra comma and space at the end of the table definition
        query.delete(query.length() - 2, query.length());
        query.append(")");

        if (!hasPrimaryKey) {
            throw new SQLException("Table " + tableName + " does not have a primary key field.");
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            statement.executeUpdate();
            System.out.println("Table " + tableName + " created successfully.");
        }
    }

    private String getDataType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR2(255)";
        } else if (type == int.class || type == Integer.class) {
            return "NUMBER";
        } else if (type == double.class || type == Double.class) {
            return "FLOAT";
        } else if (type == float.class || type == Float.class) {
            return "FLOAT";
        } else if (type == boolean.class || type == Boolean.class) {
            return "NUMBER(1)";
        } else {
            return "VARCHAR2(255)"; // By default, consider it as String
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
            values.append("?, ");
        }

        // Remove the extra comma and space at the end of the query and values
        query.delete(query.length() - 2, query.length()).append(") ");
        values.delete(values.length() - 2, values.length()).append(")");

        query.append(values);

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(index++, field.get(object));
            }
            statement.executeUpdate();
            System.out.println("Data inserted into table " + tableName + " successfully.");
        }
    }

    public <T> T selectById(Class<T> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = "id_worker";  
        
        String query = "SELECT * FROM " + tableName + " WHERE " + fieldNameId + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildInstance(clazz, resultSet);
                } else {
                    System.out.println("No record found with ID " + id + " in table " + tableName + ".");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error selecting record with ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    private <T> T buildInstance(Class<T> clazz, ResultSet resultSet) throws SQLException {
    try {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = resultSet.getObject(field.getName());

            if (value instanceof BigDecimal && field.getType() == int.class) {
                // Convert BigDecimal to int if field type is int
                value = ((BigDecimal) value).intValue();
            }

            field.set(instance, value);
        }

        return instance;
    } catch (Exception e) {
        throw new SQLException("Error building instance of class " + clazz.getName() + ": " + e.getMessage(), e);
    }
}


    // CRUD UPDATE ==========================================================================
    public void update(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String tableName = clazz.getSimpleName().toLowerCase();
            StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
    
            Field[] fields = clazz.getDeclaredFields();
            String idFieldName = null;
            Object idFieldValue = null;
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("id_worker")) {
                    query.append(field.getName()).append(" = ?, ");
                } else {
                    idFieldName = field.getName();
                    idFieldValue = field.get(object);
                }
            }
    
            // Remove the extra comma and space at the end of the query
            query.delete(query.length() - 2, query.length());
            query.append(" WHERE ").append(idFieldName).append(" = ?");
    
            try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
                int index = 1;
                for (Field field : fields) {
                    if (!field.getName().equals("id_worker")) {
                        statement.setObject(index++, field.get(object));
                    }
                }
                statement.setObject(index, idFieldValue);
    
                statement.executeUpdate();
                System.out.println("Data updated in table " + tableName + " successfully.");
            }
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error updating record: " + e.getMessage());
        }
    }
    

    // CRUD DELETE ==========================================================================
    public void delete(Class<?> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = "id_worker";  

        String query = "DELETE FROM " + tableName + " WHERE " + fieldNameId + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            statement.executeUpdate();
            System.out.println("Record deleted from table " + tableName + " successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting record with ID " + id + ": " + e.getMessage());
        }
    }

    // SELECT ALL ==========================================================================
    public <T> List<T> selectAll(Class<T> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();

        String query = "SELECT * FROM " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(buildInstance(clazz, resultSet));
            }
            return list;

        } catch (SQLException e) {
            System.err.println("Error selecting all records: " + e.getMessage());
            return null;
        }
    }
}
