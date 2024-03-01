package com.example.car;

public class Repository {
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;

    public Repository(){
        this.hasChanged = this.hasFinished = false;
        this.startTime = this.endTime = 0;
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

    public boolean getHasFinished(){
        return hasFinished;
    }
}
