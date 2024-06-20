package com.example;

import java.util.Random;

public class Mascot {
    private String type;
    private String name;
    private String breed;
    private String owner;
    private String id_mascot;

    public Mascot(String type, String name, String breed, String owner) {
        this.type = type;
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.id_mascot = generateId();
    }

    private String generateId() {
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId_mascot() {
        return id_mascot;
    }

    public void setId_mascot(String id_mascot) {
        this.id_mascot = id_mascot;
    }

    @Override
    public String toString() {
        return "Mascot{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", owner='" + owner + '\'' +
                ", id_mascot='" + id_mascot + '\'' +
                '}';
    }
}
