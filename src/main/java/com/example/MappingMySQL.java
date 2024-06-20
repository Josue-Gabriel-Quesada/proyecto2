package com.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MappingMySQL {
    private final Connection connection;

    public MappingMySQL(Connection connection) {
        this.connection = connection;
    }

    // CRUD CREATE ==========================================================================
    public void insert(Object obj) {
        String tableName = obj.getClass().getSimpleName().toLowerCase();
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                columns.append(field.getName()).append(",");
                values.append("'").append(field.get(obj)).append("',");
            }
            columns.setLength(columns.length() - 1); // Remove the trailing comma
            values.setLength(values.length() - 1); // Remove the trailing comma

            String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
                System.out.println("Record inserted into table " + tableName + " successfully.");
            }
        } catch (IllegalAccessException | SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    // CRUD READ ALL ==========================================================================
    public <T> List<T> selectAll(Class<T> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        List<T> results = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T instance = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(field.getName()));
                }
                results.add(instance);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving records: " + e.getMessage());
        }
        return results;
    }

    // CRUD READ BY ID ==========================================================================
    public <T> T selectById(Class<T> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = "id_" + tableName;
        String query = "SELECT * FROM " + tableName + " WHERE " + fieldNameId + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                T instance = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(field.getName()));
                }
                return instance;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving record by ID: " + e.getMessage());
        }
        return null;
    }

   // CRUD UPDATE ==========================================================================
public void update(Object obj) {
    String tableName = obj.getClass().getSimpleName().toLowerCase();
    String fieldNameId = "id_" + tableName;
    Field[] fields = obj.getClass().getDeclaredFields();
    StringBuilder setClause = new StringBuilder();

    try {
        Object idValue = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(fieldNameId)) {
                idValue = field.get(obj);
            } else {
                setClause.append(field.getName()).append(" = '").append(field.get(obj)).append("', ");
            }
        }
        setClause.setLength(setClause.length() - 2); // Remove the trailing comma and space

        String query = "UPDATE " + tableName + " SET " + setClause + " WHERE " + fieldNameId + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, idValue);
            statement.executeUpdate();
            System.out.println("Record updated in table " + tableName + " successfully.");
        }
    } catch (IllegalAccessException | SQLException e) {
        System.err.println("Error updating record: " + e.getMessage());
    }
}


    // CRUD DELETE ==========================================================================
    public void delete(Class<?> clazz, Object id) {
        String tableName = clazz.getSimpleName().toLowerCase();
        String fieldNameId = "id_" + tableName;

        String query = "DELETE FROM " + tableName + " WHERE " + fieldNameId + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record deleted from table " + tableName + " successfully.");
            } else {
                System.out.println("No record found with the ID in table " + tableName + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting record with ID " + id + ": " + e.getMessage());
        }
    }
}
