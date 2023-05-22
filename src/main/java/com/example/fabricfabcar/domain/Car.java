package com.example.fabricfabcar.domain;

import lombok.Data;

@Data
public class Car {
   private String key;
   private String colour;
   private String make;
   private String owner;
   private String model;

    public Car(String carnumber, String make, String model, String colour, String owner) {
        this.key = carnumber;
        this.colour = colour;
        this.make = make;
        this.owner = owner;
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