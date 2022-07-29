package ru.work.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "transmission")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public static Transmission of(int id, String name){
        Transmission transmission = new Transmission();
        transmission.id = id;
        transmission.name = name;
        return transmission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
