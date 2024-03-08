package com.example.car.Model;

import androidx.annotation.NonNull;

public class MetricSpeed implements Speed{
    private final int metersPerSecondSpeed;

    public MetricSpeed(int speed){
        this.metersPerSecondSpeed = speed;
    }

    @Override
    public int getSpeed() {
        return (int) (this.metersPerSecondSpeed * 3.6);
    }

    @NonNull
    @Override
    public String toString() {
        return Integer.toString(this.getSpeed());
    }
}
