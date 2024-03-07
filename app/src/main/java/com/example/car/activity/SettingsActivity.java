package com.example.car.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.car.MyApplication;
import com.example.car.R;
import com.example.car.Repository.DiskRepository;
import com.example.car.controller.SettingsActivityController;
import com.example.car.controller.StatisticsActivityController;
import com.example.car.controller.Validator;

public class SettingsActivity extends AppCompatActivity {

    Switch unitSwitch;

    EditText testSpeedEdit;

    Button saveExitButton;

    SettingsActivityController controller;

    boolean unitSwitchState;

    private static int defaultSpeedValue = -1;

    private void initialise(){
        MyApplication applicationClass = (MyApplication) getApplicationContext();
        controller = new SettingsActivityController(applicationClass.getResultsRepository(), this);

        unitSwitchState = false;
    }

    private void onSwitchStateChanged(boolean newState){
        this.unitSwitchState = newState;
    }

    private String getSpeedFromEdit(){
        return String.valueOf(testSpeedEdit.getText());
    }

    private String getUnitFromSwitch(){
        if (this.unitSwitchState)
            return "imperial";
        return "metric";
    }

    private void saveSettings(){
        if (Validator.isCorrectSpeed(getSpeedFromEdit())){
            controller.propagateTestSpeedChange(Integer.parseInt(getSpeedFromEdit()));
        }
        controller.propagateUnitChange(getUnitFromSwitch());
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

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSwitchStateChanged(isChecked);
            }
        });
    }
}