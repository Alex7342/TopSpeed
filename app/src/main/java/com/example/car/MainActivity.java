package com.example.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car.controller.MainActivityController;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity{
    private TextView speedTextView, timerTextView;
    private Button resetButton;
    private ProgressBar progressBar;

    private MainActivityController controller;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime = 20; // ms
    private static final int minimumUpdateDistance = 2; //m

    private void initialise() {
        MyApplication applicationClass = (MyApplication) getApplicationContext();
        controller = new MainActivityController(applicationClass.getResultsRepository(), this);

       initialiseViews();
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

    public void updateSpeedViews(int speed, double speedometerConversionCoefficient){
        speedTextView.setText(speed + " km/h");
        progressBar.setProgress((int) (speed / speedometerConversionCoefficient));
    }

    public void updateTimerView(String time){
        timerTextView.setText(time + " s");
    }

    public void initialiseViews(){
        speedTextView.setText("0 km/h");
        progressBar.setProgress(0);
        timerTextView.setText("0.00 s");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        speedTextView = findViewById(R.id.texViewProgress);
        resetButton = findViewById(R.id.resetButton);
        timerTextView = findViewById(R.id.timerTextView);

        requestPermissions();
        initialise();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

            LocationRequest locationRequest = new LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    100
            ).setIntervalMillis(100)
                    .setMinUpdateIntervalMillis(100)
                    .setWaitForAccurateLocation(false)
                    .setMaxUpdateAgeMillis(100)
                    .setMinUpdateDistanceMeters(1)
                    .build();

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()){
                        controller.onLocationChanged((int) location.getSpeed());
                    }
                }
            };

            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onResetAction();
            }
        });
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