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

    private final MongoDatabase database;

    public MappingMongo(MongoDatabase database) {
        this.database = database;
    }

    public void insert(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document doc = convertToDocument(object, clazz);

            String idFieldName = "id_mascot";
            if (doc.containsKey("_id")) {
                doc.remove("_id");
            }
            doc.put("_id", doc.get(idFieldName));
            doc.remove(idFieldName);

            collection.insertOne(doc);

            System.out.println("Objeto insertado correctamente en la colección " + collectionName);
        } catch (Exception e) {
            System.err.println("Error al insertar el objeto en MongoDB: " + e.getMessage());
        }
    }

    public <T> List<T> selectAll(Class<T> clazz) {
        List<T> results = new ArrayList<>();
        String collectionName = clazz.getSimpleName().toLowerCase();
        MongoCollection<Document> collection = database.getCollection(collectionName);

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

    public <T> T selectById(Class<T> clazz, String id) {
        String collectionName = clazz.getSimpleName().toLowerCase();
        MongoCollection<Document> collection = database.getCollection(collectionName);
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

    public void update(Object object) {
        try {
            Class<?> clazz = object.getClass();
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document doc = convertToDocument(object, clazz);
            String idFieldName = "id_mascot";

            if (!doc.containsKey("_id")) {
                throw new IllegalArgumentException("El objeto no tiene un campo _id definido.");
            }

            String id = doc.getString("_id");
            collection.replaceOne(Filters.eq("_id", id), doc);

            System.out.println("Objeto actualizado correctamente en la colección " + collectionName);
        } catch (Exception e) {
            System.err.println("Error al actualizar el objeto en MongoDB: " + e.getMessage());
        }
    }

    public void delete(Class<?> clazz, String id) {
        try {
            String collectionName = clazz.getSimpleName().toLowerCase();
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("_id", id);

            collection.deleteOne(query);

            System.out.println("Objeto eliminado correctamente de la colección " + collectionName);
        } catch (Exception e) {
            System.err.println("Error al eliminar el objeto de MongoDB: " + e.getMessage());
        }
    }

    private Document convertToDocument(Object object, Class<?> clazz) throws IllegalAccessException {
        Document doc = new Document();
        Field[] fields = clazz.getDeclaredFields();
        String idFieldName = "id_mascot";

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            doc.append(fieldName, fieldValue);

            if (fieldName.equals(idFieldName)) {
                doc.put("_id", fieldValue);
            }
        }
        return doc;
    }

    private <T> T buildInstance(Class<T> clazz, Document doc) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        String idFieldName = "id_mascot";

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            if (fieldName.equals(idFieldName)) {
                field.set(instance, doc.get("_id"));
            } else {
                field.set(instance, doc.get(fieldName));
            }
        }

        return instance;
    }
}
