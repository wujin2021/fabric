package com.example.fabricfabcar.domain;

import lombok.Data;

@Data
public class Car {
   private String key;
   private String colour;
   private String make;
   private String owner;
   private String model;


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