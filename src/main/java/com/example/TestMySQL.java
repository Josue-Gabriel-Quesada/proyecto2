package com.example;

import java.util.List;

public class TestMySQL {

    public static void createHouse(MappingMySQL mapping, String address, int numberOfRooms, double area) {
        House house = new House(address, numberOfRooms, area);
        mapping.insert(house);
        System.out.println("House created successfully in MySQL with ID: " + house.getId_house());
    }

    public static void retrieveAllHouses(MappingMySQL mapping) {
        List<House> houses = mapping.selectAll(House.class);
        if (!houses.isEmpty()) {
            for (House house : houses) {
                System.out.println("ID: " + house.getId_house() + ", Address: " + house.getAddress() +
                        ", Rooms: " + house.getNumberOfRooms() + ", Area: " + house.getArea());
            }
        } else {
            System.out.println("No houses found.");
        }
    }

    public static void updateHouse(MappingMySQL mapping, String id, String newAddress, int newRooms, double newArea) {
        House house = mapping.selectById(House.class, id);
        if (house != null) {
            house.setAddress(newAddress);
            house.setNumberOfRooms(newRooms);
            house.setArea(newArea);
            mapping.update(house);
            System.out.println("House updated successfully in MySQL.");
        } else {
            System.out.println("No house found with the specified ID in MySQL.");
        }
    }

    public static void deleteHouse(MappingMySQL mapping, String id) {
        mapping.delete(House.class, id);
        System.out.println("House with ID " + id + " deleted successfully from MySQL database.");
    }
}
