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
import java.util.Scanner;
import java.util.Set;

public class DiskRepository {
    private ArrayList<Result> entityList;
    private Context activityContext;
    private String fileName = "com.example.car.PREFERENCE_FILE_KEY";
    private SharedPreferences sharedPref;

    private static String countID = "countID";

    private static int defaultIntValue = -1;
    private static float defualtFloatValue = -1;

    private static String defaultDateTime = "2000/01/01 00:00:00";

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
    }

    public DiskRepository(Context iActivityContext) {
        this.activityContext = iActivityContext;
        this.entityList = new ArrayList<>();
        sharedPref = this.activityContext.getSharedPreferences(
                this.fileName, Context.MODE_PRIVATE);

        int count = sharedPref.getInt(countID, defaultIntValue);
        if (count != defaultIntValue)
            readFromDisk(count);
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

    public int getSize() {
        return this.entityList.size();
    }
}
