/**
 * Clase que representa a una persona.
 * 
 * <p>Esta clase modela a una persona con un identificador único, un nombre y una edad.</p>
 * 
 * <p>El identificador único se genera automáticamente al crear una nueva instancia de Persona.</p>
 * 
 * <p>Esta clase se utiliza junto con la clase Mapping para mapear objetos Persona a tablas de base de datos y viceversa.</p>
 * 
 * @author Josue Gabriel
 * @version 23/05/2024
 */
package com.example;

public class Person {
    private String id;
    private String name;
    private int age;

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
