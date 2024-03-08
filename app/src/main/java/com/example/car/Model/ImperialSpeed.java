package com.example.car.Model;

import androidx.annotation.NonNull;

public class ImperialSpeed implements Speed{

    private final int metersPerSecondSpeed;

    public ImperialSpeed(int speed){
        this.metersPerSecondSpeed = speed;
    }

    @Override
    public int getSpeed() {
        return (int) (this.metersPerSecondSpeed * 2.237);
    }

    @NonNull
    @Override
    public String toString() {
        return Integer.toString(this.getSpeed());
    }
}
