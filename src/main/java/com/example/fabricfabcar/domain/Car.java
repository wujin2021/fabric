package com.example.fabricfabcar.domain;

public class Car {
   private String key;
   private String colour;
   private String make;
   private String owner;
   private String model;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    @Override
    public String toString() {
        return "Car{" +
                "key='" + key + '\'' +
                ", colour='" + colour + '\'' +
                ", make='" + make + '\'' +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
