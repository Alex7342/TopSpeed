package com.example.car;

public class Result {
    private final double time_0_100;
    private final int maxSpeed;
    private final double averageSpeed;
    private final String dateTime;

    public Result(double time_0_100, int maxSpeed, double averageSpeed, String dateTime) {
        this.time_0_100 = time_0_100;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return this.dateTime;
    }
}
