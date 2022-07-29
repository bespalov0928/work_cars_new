package ru.work.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "body_id")
    private Body body;
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @ManyToOne
    @JoinColumn(name = "mark_id")
    private Mark mark;
    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;
    private String year;

    public static Car of(String name, Engine engine, Mark mark, Body body, String year) {
        Car car = new Car();
        car.name = name;
        car.engine = engine;
        car.mark = mark;
        car.body = body;
        car.year = year;
        return car;
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

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
