package com.example.car.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.car.MyApplication;
import com.example.car.R;
import com.example.car.Repository.DiskRepository;
import com.example.car.controller.SettingsActivityController;
import com.example.car.controller.StatisticsActivityController;

public class SettingsActivity extends AppCompatActivity {

    Switch unitSwitch;

    EditText testSpeedEdit;

    Button saveExitButton;

    SettingsActivityController controller;

    private void initialise(){
        MyApplication applicationClass = (MyApplication) getApplicationContext();
        controller = new SettingsActivityController(applicationClass.getResultsRepository(), this);
    }

    private void saveSettings(){
        //TODO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        unitSwitch = findViewById(R.id.unitSwitch);
        testSpeedEdit = findViewById(R.id.testSpeedEdit);
        saveExitButton = findViewById(R.id.saveExitButton);

        ActionBar activityActionBar = getSupportActionBar();
        if (activityActionBar != null)
            activityActionBar.setTitle("Settings");

        initialise();

        saveExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                finish();
            }
        });
    }
}