package com.example.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private TextView speedTextView;
    private ProgressBar progressBar;

    private Controller controller;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime = 0; // ms
    private static final int minimumUpdateDistance = 0; //m

    private void initialise() {
        controller = new Controller();

        speedTextView.setText("0 km/h");
        progressBar.setProgress(0);
    }

    private void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, locationRequestCode);
        }
        else
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
    }

    private void startResultsActivity(){
       Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
       Result lastResult = this.controller.getLastResult();

       intent.putExtra("totalTime", Double.toString(lastResult.getTime_0_100()));
       intent.putExtra("averageSpeed", Double.toString(lastResult.getAverageSpeed()));
       intent.putExtra("maxSpeed", Integer.toString(lastResult.getMaxSpeed()));
       startActivity(intent);
    }

    void addDemoEntity(){
        controller.addResult();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        speedTextView = findViewById(R.id.texViewProgress);
        
        requestPermissions();
        initialise();
        //addDemoEntity();
        //startResultsActivity();
        
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumUpdateTime, minimumUpdateDistance, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    int speed = (int) (location.getSpeed() * 3.6);
                    speedTextView.setText(speed + " km/h");
                    progressBar.setProgress(speed);

                    controller.updateMaxSpeed(speed);

                    if (!controller.getHasChanged() && speed > 0) {
                        controller.setHasChanged(true);
                        controller.setStartTime(System.currentTimeMillis());
                    }

                    if (speed >= 100 && !controller.getHasFinished()) {
                        controller.setHasFinished(true);
                        controller.setEndTime(System.currentTimeMillis());
                        controller.addResult();
                        startResultsActivity();
                    }

                    //long millisTime = System.currentTimeMillis() - repository.getStartTime();
                    //double seconds = millisTime / 1000.0;
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationRequestCode){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
        }
    }
}