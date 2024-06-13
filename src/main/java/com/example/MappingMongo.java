package com.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MappingMongo {
    private MongoDatabase connection;

    public MappingMongo(MongoDatabase connection) {
        this.connection = connection;
    }

    // ======================= Crud Insertar ========================
    public void insert(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = connection.getCollection(collectionName);

            // Convertir el objeto a un documento MongoDB
            Document doc = convertToJson(object, clazz);

            // Insertar el documento en la colección
            collection.insertOne(doc);
            System.out.println("Documento insertado en la colección " + collectionName + " correctamente.");
        } catch (Exception e) {
            System.err.println("Error al insertar el objeto en MongoDB: " + e.getMessage());
        }
    }

    private Document convertToJson(Object object, Class<?> clazz) throws IllegalAccessException {
        Document doc = new Document();
        Field[] fields = clazz.getDeclaredFields();
        String idFieldName = clazz.getSimpleName().toLowerCase() + "_id";

        for (Field field : fields) {
            field.setAccessible(true); // Permitir acceso a campos privados
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            if (fieldName.equals(idFieldName)) {
                doc.append("_id", fieldValue); // Si el campo es el id, se añade como _id
            } else {
                doc.append(fieldName, fieldValue);
            }
        }
        return doc;
    }

    // ===================== Crud seleccionar =============================
    // Seleccionar todo
    public <T> List<T> selectAll(Class<T> clazz) {
        List<T> results = new ArrayList<>();
        String collectionName = clazz.getSimpleName().toLowerCase();
        MongoCollection<Document> collection = connection.getCollection(collectionName);

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                T instance = buildInstance(clazz, doc);
                results.add(instance);
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar objetos de la colección: " + e.getMessage());
        }
        return results;
    }

    // Seleccionar uno
    public <T> T selectById(Class<T> clazz, Object id) {
        String collectionName = clazz.getSimpleName().toLowerCase();
        MongoCollection<Document> collection = connection.getCollection(collectionName);
        Document query = new Document("_id", id);

        try {
            Document doc = collection.find(query).first();
            if (doc != null) {
                return buildInstance(clazz, doc);
            }
        } catch (Exception e) {
            System.err.println("Error al recuperar el objeto de la colección: " + e.getMessage());
        }
        return null;
    }

    private <T> T buildInstance(Class<T> clazz, Document doc) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = doc.get(fieldName);

            if (fieldValue == null && fieldName.equals(clazz.getSimpleName().toLowerCase() + "_id")) {
                fieldValue = doc.get("_id");
            }

            field.set(instance, fieldValue);
        }

        return instance;
    }

    // ============== Crud Eliminar ====================================
    public <T> void delete(Class<T> clazz, Object id) {
        try {
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = connection.getCollection(collectionName);
            Document query = new Document("_id", id);
    
            collection.deleteOne(query);
            System.out.println("Documento eliminado de la colección " + collectionName + " correctamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar el objeto de MongoDB: " + e.getMessage());
        }
    }

    // ============== Crud Modificar ===================================
    public void update(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = connection.getCollection(collectionName);

            // Convertir el objeto a un documento MongoDB
            Document doc = convertToJson(object, clazz);
            String idFieldName = clazz.getSimpleName().toLowerCase() + "_id";
            Field idField = clazz.getDeclaredField(idFieldName);
            idField.setAccessible(true);
            Object id = idField.get(object);

            if (id == null) {
                throw new IllegalArgumentException("El campo ID no puede ser nulo");
            }

            // Reemplazar el documento en la colección
            collection.replaceOne(Filters.eq("_id", id), doc);
            System.out.println("Documento actualizado en la colección " + collectionName + " correctamente.");
        } catch (Exception e) {
            System.err.println("Error al actualizar el objeto en MongoDB: " + e.getMessage());
        }
    }
}
