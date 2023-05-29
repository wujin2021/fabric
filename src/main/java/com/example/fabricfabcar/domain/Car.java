package com.example.fabricfabcar.domain;

import lombok.Data;

@Data
public class Car {
   private String key;
   private String colour;
   private String make;
   private String owner;
   private String model;

//    public Car(String carnumber, String make, String model, String colour, String owner) {
//        this.key = carnumber;
//        this.colour = colour;
//        this.make = make;
//        this.owner = owner;
//        this.model = model;
//    }
public String getKey() {
    return key;
}

    public void setKey(String key) {
        this.key = key;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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