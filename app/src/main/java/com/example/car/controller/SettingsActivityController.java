package com.example.car.controller;

import android.content.Context;

import com.example.car.Repository.DiskRepository;

public class SettingsActivityController {

    private DiskRepository repository;
    private Context activityContext;

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
