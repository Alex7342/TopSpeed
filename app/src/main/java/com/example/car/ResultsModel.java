package com.example.car;

public class ResultsModel {
    private String totalTime;
    private String averageSpeed;
    private String maxSpeed;


    public ResultsModel(String totalTime, String averageSpeed, String maxSpeed) {
        this.totalTime = totalTime;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }
}
