package com.example.car;

public class Result {
    private final double time_0_100;
    private final int maxSpeed;
    private final double averageSpeed;

    public Result(double time_0_100, int max_speed, double average_speed) {
        this.time_0_100 = time_0_100;
        this.maxSpeed = max_speed;
        this.averageSpeed = average_speed;
    }

    public double getTime_0_100() {
        return this.time_0_100;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public double getAverageSpeed() {
        return this.averageSpeed;
    }
}
