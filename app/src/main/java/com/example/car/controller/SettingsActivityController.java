package com.example.car.controller;

import android.content.Context;

import com.example.car.Repository.DiskRepository;

import java.util.Objects;

public class SettingsActivityController {

    private DiskRepository repository;
    private Context activityContext;

    public int getRepoTestSpeed(){
        return repository.getTestSpeed();
    }

    public boolean getRepoUnit(){
        return !Objects.equals(repository.getUnit(), "metric");
    }

    public void propagateUnitChange(String newUnit){
        repository.onUnitChanged(newUnit);
    }

    public void propagateTestSpeedChange(int newSpeed){
        repository.onTestSpeedChanged(newSpeed);
    }

    public SettingsActivityController(DiskRepository iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
    }
}
