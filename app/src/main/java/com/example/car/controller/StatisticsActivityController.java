package com.example.car.controller;

import android.content.Context;

import com.example.car.Repository.IRepository;
import com.example.car.Result;

import java.util.ArrayList;

public class StatisticsActivityController {

    private IRepository<Result> repository;
    private Context activityContext;


    public StatisticsActivityController(IRepository<Result> iRepository, Context iActivityContext){
        this.repository = iRepository;
        this.activityContext = iActivityContext;
    }

    public ArrayList<Result> getResultsList(){
        return repository.getAllEntities();
    }
}
