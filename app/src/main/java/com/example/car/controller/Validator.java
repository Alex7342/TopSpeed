package com.example.car.controller;

public class Validator {
    public static boolean isCorrectSpeed(String speed){
        try {
            int parsedSpeed = Integer.parseInt(speed);
            return parsedSpeed > 0;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
