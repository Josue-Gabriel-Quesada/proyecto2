package com.example;

import java.math.BigDecimal;

public class Worker {

    private String id_worker;
    private String name;
    private BigDecimal age;
    private String position;

    public Worker(String name, BigDecimal age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.id_worker = generateId(); // Genera el ID aquí en lugar de en el constructor
    }

    public Worker() {
        // Constructor predeterminado necesario para el mapeo de objetos
    }

    private String generateId() {
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    // Getters y Setters
    public String getId_worker() {
        return id_worker;
    }

    public void setId_worker(String id_worker) {
        this.id_worker = id_worker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id_worker='" + id_worker + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                '}';
    }
}
