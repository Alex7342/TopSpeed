package com.example.car.controller;

import static androidx.core.content.ContextCompat.startActivity;
import static java.lang.Math.max;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.car.MainActivity;
import com.example.car.Repository.IRepository;
import com.example.car.Result;
import com.example.car.ResultsActivity;
import com.example.car.activity.StatisticsActivity;

import java.util.ArrayList;

public class MainActivityController{
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;
    private int maxSpeed;
    private int currentSpeed;
    private ArrayList<Integer> speedList;

    private IRepository<Result> repository;
    private Context activityContext;

    private final int testSpeed = 30;
    private final double speedConversionCoeff = 2.5;

    public MainActivityController(IRepository<Result> iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
        this.speedList = new ArrayList<>();
        this.resetSession();
    }

    public void resetSession(){
        this.hasChanged = this.hasFinished = false;
        this.startTime = this.endTime = this.maxSpeed = this.currentSpeed = 0;
        this.speedList.clear();
        this.updateActivitySpeedViews();
        this.updateActivityTimer();
    }
    private void updateSpeedVariables(int currentSpeed){
        this.currentSpeed = currentSpeed;
        this.maxSpeed = max(this.maxSpeed, currentSpeed);
        this.speedList.add(currentSpeed);
    }

    public void startResultsActivity(){
        Intent intent = new Intent(this.activityContext, ResultsActivity.class);
        Result lastResult = this.getLastResult();

        intent.putExtra("totalTime", Double.toString(lastResult.getTime_0_100()));
        intent.putExtra("averageSpeed", Double.toString(lastResult.getAverageSpeed()));
        intent.putExtra("maxSpeed", Integer.toString(lastResult.getMaxSpeed()));
        startActivity(this.activityContext, intent, null);
    }

    public void startSettingsActivity(){

    }

    public void startStatisticsActivity(){
        startActivity(this.activityContext, new Intent(activityContext, StatisticsActivity.class), null);
    }

    private String getCurrentTimePeriod(){
        if (this.startTime == 0)
            return "0";
        return Double.toString((System.currentTimeMillis() - this.startTime) / 1000.0);
    }

    private void updateActivityTimer(){
        ((MainActivity) activityContext).updateTimerView(getCurrentTimePeriod());
    }

    private void updateActivitySpeedViews(){
        ((MainActivity) activityContext).updateSpeedViews(this.currentSpeed, this.speedConversionCoeff);
    }

    public void onLocationChanged(int locationSpeed){
        if (this.currentSpeed != locationSpeed)
            this.onSpeedChanged(locationSpeed);
        updateActivityTimer();
    }

    private void onSpeedChanged(int currentSpeed){
        updateSpeedVariables(currentSpeed);
        updateActivitySpeedViews();

        if (!hasChanged && currentSpeed > 0){
            this.hasChanged = true;
            this.startTime = System.currentTimeMillis();
        }

        if (!hasFinished && currentSpeed >= testSpeed){
            this.hasFinished = true;
            this.endTime = System.currentTimeMillis();
            this.addResult();
            this.startResultsActivity();
        }
    }

    public void onResetAction(){
        this.resetSession();
    }

    private double computeTotalTime(){
        return 1.0 * (this.endTime - this.startTime) / 1000;
    }

    private void addResult() {
        this.repository.addEntity(new Result(this.computeTotalTime(), this.maxSpeed, this.getAverageSpeed()));
    }

    private Result getLastResult(){
        ArrayList<Result> resultList = this.repository.getAllEntities();

        if (resultList.size() > 0)
            return resultList.get(resultList.size() - 1);
        else
            return new Result(0, 0, 0);
    }

    private double getAverageSpeed(){
        double average = 0;
        for (int speed : speedList){
            average += speed;
        }
        return average / speedList.size();
    }
}
