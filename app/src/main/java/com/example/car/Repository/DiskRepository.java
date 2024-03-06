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

public class DiskRepository <E> implements IRepository<E> {
    private ArrayList<E> entityList;
    private Context activityContext;
    private String fileName = "com.example.car.PREFERENCE_FILE_KEY";
    private SharedPreferences sharedPref;

    private static String countID = "countID";

    private static int defaultIntValue = -1;
    private static float defualtFloatValue = -1;

    private String getTimeID(int position) {
        return Integer.toString(position) + "_Time";
    }

    private String getMaxSpeedID(int position) {
        return Integer.toString(position) + "_MaxSpeed";
    }

    private String getAverageSpeedID(int position) {
        return Integer.toString(position) + "_AverageSpeed";
    }

    private void readFromDisk(int count) {
        for (int i = 0; i < count; i++) {
            double time = sharedPref.getFloat(getTimeID(i), defualtFloatValue);
            int maxSpeed = sharedPref.getInt(getMaxSpeedID(i), defaultIntValue);
            double averageSpeed = sharedPref.getFloat(getAverageSpeedID(i), defualtFloatValue);
            //this.entityList.add(new Result(time, maxSpeed, averageSpeed));
        }
    }

    public DiskRepository(Context iActivityContext) {
        this.activityContext = iActivityContext;
        sharedPref = this.activityContext.getSharedPreferences(
                this.fileName, Context.MODE_PRIVATE);

        int count = sharedPref.getInt(countID, defaultIntValue);
        if (count != defaultIntValue)
            readFromDisk(count);
    }

    @Override
    public void addEntity(E newEntity) {
        this.entityList.add(newEntity);
        //this.write();
    }

    @Override
    public ArrayList<E> getAllEntities() {
        return this.entityList;
    }

    public int getSize() {
        return this.entityList.size();
    }
}
