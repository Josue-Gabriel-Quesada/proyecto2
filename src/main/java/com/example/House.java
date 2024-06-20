package com.example;
import java.util.Random;


public class House {
    private String id_house;
    private String address;
    private int numberOfRooms;
    private double area;

    public House(String address, int numberOfRooms, double area) {
        this.address = address;
        this.numberOfRooms = numberOfRooms;
        this.area = area;
        this.id_house = generateId(); // Genera el ID aquí en lugar de en el constructor
    }

    public House() {
        // Constructor predeterminado necesario para el mapeo de objetos
    }

    private String generateId() {
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    // Getters y Setters
    public String getId_house() {
        return id_house;
    }

    public void setId_house(String id_house) {
        this.id_house = id_house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "House{" +
                "id_house='" + id_house + '\'' +
                ", address='" + address + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", area=" + area +
                '}';
    }

    public static void main(String[] args) {
        House house1 = new House("123 Main St", 4, 250.5);
        House house2 = new House("456 Elm St", 3, 200.0);

        System.out.println(house1);
        System.out.println(house2);
    }
}
