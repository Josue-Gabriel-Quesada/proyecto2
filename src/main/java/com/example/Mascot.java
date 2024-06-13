package com.example;

public class Mascot {
    private String type;
    private String name;
    private String breed;
    private String owner;
    private String id;

    public Mascot(String type, String name, String breed, String owner) {
        this.type = type;
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.id = generateId();
    }

    // Método para generar un ID aleatorio
    private String generateId() {
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = (int)(Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    // Getters and setters
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mascot{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", owner='" + owner + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
