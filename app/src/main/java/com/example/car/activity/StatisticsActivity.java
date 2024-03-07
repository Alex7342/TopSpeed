package com.example.car.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.car.MyApplication;
import com.example.car.R;
import com.example.car.ResultAdaptor;
import com.example.car.controller.MainActivityController;
import com.example.car.controller.StatisticsActivityController;

public class StatisticsActivity extends AppCompatActivity {

    ListView resultsListView;

    Button statisticsExitButton;

    StatisticsActivityController controller;

    private void initialise(){
        MyApplication applicationClass = (MyApplication) getApplicationContext();
        controller = new StatisticsActivityController(applicationClass.getResultsRepository(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        resultsListView = findViewById(R.id.statisticsListView);
        statisticsExitButton = findViewById(R.id.statisticsExitButton);
        ActionBar activityActionBar = getSupportActionBar();
        if (activityActionBar != null)
            activityActionBar.setTitle("Statistics");

        initialise();

        ResultAdaptor resultAdaptor = new ResultAdaptor(getApplicationContext(), controller.getResultsList());
        resultsListView.setAdapter(resultAdaptor);

        statisticsExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}