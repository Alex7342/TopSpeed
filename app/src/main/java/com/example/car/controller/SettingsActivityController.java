package com.example.car.controller;

import android.content.Context;

import com.example.car.Repository.DiskRepository;

public class SettingsActivityController {

    private DiskRepository repository;
    private Context activityContext;

    public SettingsActivityController(DiskRepository iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
    }
}
