package com.example.car;

import static java.lang.Math.max;

public class Controller {
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;
    private int maxSpeed;
    private Repository repository;
    public Controller(){
        this.hasChanged = this.hasFinished = false;
        this.startTime = this.endTime = 0;
        this.maxSpeed = 0;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean getHasChanged() {
        return hasChanged;
    }

    public boolean getHasFinished() {
        return hasFinished;
    }

    public void updateMaxSpeed(int currentSpeed) {
        this.maxSpeed = max(this.maxSpeed, currentSpeed);
    }

    public void addResult() {
        double time_0_100 = 1.0 * (this.endTime - this.startTime) / 1000;
        this.repository.addResult(new Result(time_0_100, this.maxSpeed, 0));
    }
}
