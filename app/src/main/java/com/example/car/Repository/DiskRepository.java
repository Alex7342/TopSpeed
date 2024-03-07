package com.example.car.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.car.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class DiskRepository {
    private ArrayList<Result> entityList;
    private String unit;
    private int testSpeed;
    private Context activityContext;
    private String fileName = "com.example.car.PREFERENCE_FILE_KEY";
    private SharedPreferences sharedPref;
    private static String countID = "countID";
    private static String unitID = "unitID";
    private static String testSpeedID = "testSpeedID";
    private static int defaultIntValue = -1;
    private static float defualtFloatValue = -1;
    private static String defaultDateTime = "2000/01/01 00:00:00";
    private static String defaultUnit = "metric";
    private static int defaultMetricTestSpeed = 100;
    private static int defaultImperialTestSpeed = 60;
    private static int defaultTestSpeed = -1;

    private String getTimeID(int position) {
        return Integer.toString(position) + "_Time";
    }

    private String getMaxSpeedID(int position) {
        return Integer.toString(position) + "_MaxSpeed";
    }

    private String getAverageSpeedID(int position) {
        return Integer.toString(position) + "_AverageSpeed";
    }

    private String getDateTimeID(int position) {
        return Integer.toString(position) + "_DateTime";
    }

    private void readFromDisk(int count) {
        for (int i = 0; i < count; i++) {
            double time = sharedPref.getFloat(getTimeID(i), defualtFloatValue);
            int maxSpeed = sharedPref.getInt(getMaxSpeedID(i), defaultIntValue);
            double averageSpeed = sharedPref.getFloat(getAverageSpeedID(i), defualtFloatValue);
            String dateTime = sharedPref.getString(getDateTimeID(i), defaultDateTime);
            this.entityList.add(new Result(time, maxSpeed, averageSpeed, dateTime));
        }

        this.unit = this.sharedPref.getString(unitID, defaultUnit);
        this.testSpeed = this.sharedPref.getInt(testSpeedID, defaultTestSpeed);

        if (this.testSpeed == defaultTestSpeed) {
            if (Objects.equals(this.unit, "metric"))
                this.testSpeed = defaultMetricTestSpeed;
            else
                this.testSpeed = defaultImperialTestSpeed;
        }
    }

    public DiskRepository(Context iActivityContext) {
        this.activityContext = iActivityContext;
        this.entityList = new ArrayList<>();
        sharedPref = this.activityContext.getSharedPreferences(
                this.fileName, Context.MODE_PRIVATE);

        int count = sharedPref.getInt(countID, defaultIntValue);
        if (count != defaultIntValue)
            readFromDisk(count);

        Log.d("UNIT_SPEED", this.unit);
        Log.d("UNIT_SPEED", Integer.toString(this.testSpeed));
    }

    public void addEntity(Result newEntity) {
        this.entityList.add(newEntity);

        SharedPreferences.Editor editor = this.sharedPref.edit();
        int count = sharedPref.getInt(countID, defaultIntValue);

        if (count == defaultIntValue)
            count = 0;

        editor.putFloat(this.getTimeID(count), (float) newEntity.getTime_0_100());
        editor.putInt(this.getMaxSpeedID(count), newEntity.getMaxSpeed());
        editor.putFloat(this.getAverageSpeedID(count), (float) newEntity.getAverageSpeed());
        editor.putString(this.getDateTimeID(count), newEntity.getDateTime());
        editor.putInt(countID, count + 1);
        editor.apply();
    }

    public ArrayList<Result> getAllEntities() {
        return this.entityList;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getTestSpeed() {
        return this.testSpeed;
    }

    public int getSize() {
        return this.entityList.size();
    }

    private int getLastTestSpeed() {
        return this.sharedPref.getInt(testSpeedID, defaultTestSpeed);
    }

    public void onUnitChanged(String newUnit) {
        if (Objects.equals(newUnit, "metric") || Objects.equals(newUnit, "imperial"))
            this.unit = newUnit;

        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(unitID, this.unit);
        editor.apply();

        Log.d("UNIT_SPEED", this.unit);
        Log.d("UNIT_SPEED", Integer.toString(this.testSpeed));
    }

    public void onTestSpeedChanged(int newSpeed) {
        if (newSpeed == defaultTestSpeed) {
            int lastTestSpeed = this.getLastTestSpeed();

            if (lastTestSpeed == defaultTestSpeed) {
                if (Objects.equals(this.unit, "metric"))
                    this.testSpeed = defaultMetricTestSpeed;
                else
                    this.testSpeed = defaultImperialTestSpeed;
            }
            else {
                this.testSpeed = lastTestSpeed;
            }
        }
        else {
            this.testSpeed = newSpeed;
        }

        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putInt(testSpeedID, this.testSpeed);
        editor.apply();

        Log.d("UNIT_SPEED", this.unit);
        Log.d("UNIT_SPEED", Integer.toString(this.testSpeed));
    }
}
