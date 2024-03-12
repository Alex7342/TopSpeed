package com.example.car.controller;

import static androidx.core.content.ContextCompat.startActivity;
import static java.lang.Math.max;

import android.content.Context;
import android.content.Intent;

import com.example.car.MainActivity;
import com.example.car.Repository.DiskRepository;
import com.example.car.Result;
import com.example.car.ResultsActivity;
import com.example.car.activity.SettingsActivity;
import com.example.car.activity.StatisticsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import android.os.Handler;

public class MainActivityController{
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;
    private int maxSpeed;
    private int currentSpeed;
    private ArrayList<Integer> speedList;

    private DiskRepository repository;
    private Context activityContext;

    private  int testSpeed;
    private double speedConversionCoeff;

    private int timerTimeMs;

    boolean isTimerRunning;

    public MainActivityController(DiskRepository iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
        this.speedList = new ArrayList<>();
        this.resetSession();
    }

    public void resetSession(){
        this.hasChanged = this.hasFinished = false;
        this.startTime = this.endTime = this.maxSpeed = this.currentSpeed = 0;
        this.speedConversionCoeff = (double) repository.getTestSpeed() / 100;
        this.speedList.clear();
        this.resetTimer();
        this.updateActivitySpeedViews();
        this.resetActivityTimerView();
        this.testSpeed = repository.getTestSpeed();
    }
    private void updateSpeedVariables(int currentSpeed){
        this.currentSpeed = currentSpeed;
        this.maxSpeed = max(this.maxSpeed, currentSpeed);
        this.speedList.add(currentSpeed);
    }

    public String getMetricUnitString(){
        if (Objects.equals(this.repository.getUnit(), "metric"))
            return " km/h";
        return " mph";
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
        startActivity(this.activityContext, new Intent(activityContext, SettingsActivity.class), null);
    }

    public void startStatisticsActivity(){
        startActivity(this.activityContext, new Intent(activityContext, StatisticsActivity.class), null);
    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void resetActivityTimerView(){
        ((MainActivity) activityContext).updateTimerView("0");
    }

    private void updateActivitySpeedViews(){
        ((MainActivity) activityContext).updateSpeedViews(this.currentSpeed, this.speedConversionCoeff);
    }

    public void runTimer(){
        this.isTimerRunning = true;
    }

    public void stopTimer(){
        this.isTimerRunning = false;
    }

    public void resetTimer(){
        this.isTimerRunning = false;
        this.timerTimeMs = 0;
    }

    public void createTimer(){
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isTimerRunning) {

                    double seconds = timerTimeMs / 1000.0;

                    ((MainActivity) activityContext).updateTimerView(Double.toString(seconds));

                    timerTimeMs += 200;
                }
                handler.postDelayed(this, 200);
            }
        });
    }

    public void onLocationChanged(int locationSpeed){
        // speed is given in kn / h
        if (!this.repository.isMetric())
            locationSpeed = (int) (locationSpeed / 1.6093);

        if (this.currentSpeed != locationSpeed)
            this.onSpeedChanged(locationSpeed);
    }

    private void onSpeedChanged(int currentSpeed){
        updateSpeedVariables(currentSpeed);
        updateActivitySpeedViews();

        if (!hasChanged && currentSpeed > 0){
            this.hasChanged = true;
            this.startTime = System.currentTimeMillis();
            this.runTimer();
        }

        if (!hasFinished && currentSpeed >= testSpeed){
            this.hasFinished = true;
            this.endTime = System.currentTimeMillis();
            this.addResult();
            this.startResultsActivity();
            this.resetTimer();
        }
    }

    public void onResetAction(){
        this.resetSession();
    }

    private double computeTotalTime(){
        return 1.0 * (this.endTime - this.startTime) / 1000;
    }

    private void addResult() {
        this.repository.addEntity(new Result(this.computeTotalTime(), this.maxSpeed, this.getAverageSpeed(), this.getCurrentDateTime()));
    }

    private Result getLastResult(){
        ArrayList<Result> resultList = this.repository.getAllEntities();

        if (resultList.size() > 0)
            return resultList.get(resultList.size() - 1);
        else
            return new Result(0, 0, 0, "2000/01/01 00:00:00");
    }

    private double getAverageSpeed(){
        double average = 0;
        for (int speed : speedList){
            average += speed;
        }
        return average / speedList.size();
    }
}
