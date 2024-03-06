package com.example.car.controller;

import android.content.Context;

import com.example.car.Repository.DiskRepository;
import com.example.car.Repository.IRepository;
import com.example.car.Result;

import java.util.ArrayList;

public class StatisticsActivityController {

    private DiskRepository repository;
    private Context activityContext;


    public StatisticsActivityController(DiskRepository iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
    }

    public ArrayList<Result> getResultsList(){
        return repository.getAllEntities();
    }
}
