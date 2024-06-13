package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
            // Check if it is the primary key (the primary key should be named classname_id, all in lowercase)
            if (fieldName.equals(tableName + "_id")) {
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
            values.append("'").append(fieldValue).append("', ");
        }

        // Remove the extra comma and space at the end of the field and value lists
        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());

        query.append(") ");
        values.append(")");

        String insertQuery = query.toString() + values.toString();

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.executeUpdate();
            System.out.println("Data inserted into table " + tableName + " successfully.");
        }
    }
    //==========================================================================

    // CRUD SELECT ==========================================================================
    // CRUD: SELECT ALL RECORDS
    public <T> List<T> selectAll(Class<T> clazz) {
        List<T> results = new ArrayList<>();
        String tableName = clazz.getSimpleName().toLowerCase(); // Derive the table name from the class name
        try {
            String query = "SELECT * FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        T instance = buildInstance(clazz, resultSet);
                        results.add(instance);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving objects from table: " + e.getMessage());
        }
        return results;
    }
    
    // CRUD: SELECT A RECORD
    public <T> T selectById(Class<T> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = tableName + "_id";
    
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
                String fieldName = field.getName();
                Object fieldValue = resultSet.getObject(fieldName);

                if (fieldValue != null) {
                    // Convert types if necessary
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        fieldValue = ((Number) fieldValue).intValue();
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        fieldValue = ((Number) fieldValue).doubleValue();
                    } else if (field.getType() == float.class || field.getType() == Float.class) {
                        fieldValue = ((Number) fieldValue).floatValue();
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        fieldValue = ((Number) fieldValue).longValue();
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        fieldValue = (fieldValue instanceof Number) ? ((Number) fieldValue).intValue() != 0 : Boolean.parseBoolean(fieldValue.toString());
                    }

                    field.set(instance, fieldValue);
                }
            }

            return instance;
        } catch (NoSuchMethodException e) {
            throw new SQLException("Could not find the default constructor for class " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        } catch (InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new SQLException("Error instantiating class " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
    //========================================================================================

    // CRUD DELETE ==========================================================================
    public void delete(Class<?> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = tableName + "_id";
    
        String query = "DELETE FROM " + tableName + " WHERE " + fieldNameId + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record with ID " + id + " deleted successfully from table " + tableName + ".");
            } else {
                System.out.println("No record found with ID " + id + " in table " + tableName + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting record with ID " + id + ": " + e.getMessage());
        }
    }
    //==========================================================================================

    // CRUD UPDATE ==========================================================================
    public void update(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String tableName = clazz.getSimpleName().toLowerCase(); // Assuming the table name is the same as the class name in lowercase
            
            // Check if the table exists
            if (!tableExists(tableName)) {
                System.err.println("Table " + tableName + " does not exist.");
                return;
            }
    
            // Modify data in the table
            updateData(tableName, clazz, object);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error updating class in table: " + e.getMessage());
        }
    }
    
    private void updateData(String tableName, Class<?> clazz, Object object) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
    
        Field[] fields = clazz.getDeclaredFields();
        String fieldNameId = tableName.toLowerCase() + "_id";
        boolean found = false;
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals(fieldNameId)) {
                found = true;
                continue; // Skip the ID field
            }
            @SuppressWarnings("unused")
            Object fieldValue = field.get(object);
            query.append(fieldName).append(" = ?, ");
        }
    
        if (!found) {
            throw new SQLException("ID field not found in class " + clazz.getSimpleName());
        }
    
        // Remove the extra comma and space at the end of the field list
        query.delete(query.length() - 2, query.length());
    
        // Add WHERE condition with the ID
        query.append(" WHERE ").append(fieldNameId).append(" = ?");
    
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Set values for the fields
            int index = 1;
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.equals(fieldNameId)) {
                    continue; // Skip the ID field
                }
                Object fieldValue = field.get(object);
                statement.setObject(index, fieldValue);
                index++;
            }
    
            // Set the ID value
            Field idField;
            try {
                idField = clazz.getDeclaredField(fieldNameId);
                idField.setAccessible(true);
                Object id = idField.get(object);
                statement.setObject(index, id);
            } catch (NoSuchFieldException e) {
                throw new SQLException("ID field not found in class " + clazz.getSimpleName());
            }
    
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Data modified in table " + tableName + " successfully.");
            } else {
                System.out.println("No record found with the ID in table " + tableName + ".");
            }
        }
    }

}
