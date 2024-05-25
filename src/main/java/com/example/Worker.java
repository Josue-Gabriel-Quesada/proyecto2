/**
 * Clase que representa a un trabajador.
 * 
 * <p>Esta clase modela a un trabajador con un identificador único, un nombre, una edad y un puesto.</p>
 * 
 * <p>El identificador único se genera automáticamente al crear una nueva instancia de Worker.</p>
 * 
 * <p>Esta clase se utiliza junto con la clase Mapping para mapear objetos Worker a tablas de base de datos y viceversa.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

public class Worker {

    private String id;
    private String name;
    private int age;
    private String position;

    public Worker(String name, int age, String position) {
        this.id = generateId();
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String generateId() {
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 4;
        for (int i = 0; i < length; i++) {
            int index = (int)(Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
