package com.example.car;

import android.app.Application;

import com.example.car.Repository.IRepository;
import com.example.car.Repository.InMemoryRepository;

public class MyApplication extends Application {
    private IRepository<Result> resultsRepository;
    @Override
    public void onCreate() {
        super.onCreate();
        resultsRepository = new InMemoryRepository<>();
    }

    public IRepository<Result> getResultsRepository(){
        return this.resultsRepository;
    }
}
