package com.example.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private TextView speedTextView;
    private TextView resultTextView;
    private SeekBar speedSeekBar;

    private TextView timeElapsedView;
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime= 50; // ms
    private static final int minimumUpdateDistance = 0; //m

    private void initialise(){
        hasChanged = false;
        hasFinished = false;

        speedTextView.setText("0 km/h");
        speedSeekBar.setProgress(0);

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedTextView = findViewById(R.id.speedTextView);
        resultTextView = findViewById(R.id.resultTextView);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        timeElapsedView = findViewById(R.id.timeElapsedView);
        hasChanged = false;
        hasFinished = false;
        
        requestPermissions();
        initialise();
        
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumUpdateTime, minimumUpdateDistance, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    int speed = (int) (location.getSpeed() * 3.6);
                    speedTextView.setText(speed + " km/h");
                    speedSeekBar.setProgress(speed);

                    if (!hasChanged) {
                        hasChanged = true;
                        startTime = System.currentTimeMillis();
                    }

                    if (speed >= 100 && !hasFinished) {
                        hasFinished = true;
                        endTime = System.currentTimeMillis();
                        resultTextView.setText("Result: " + computeTimeElapsed() + " s");
                    }

                    timeElapsedView.setText(Double.toString(Math.round(System.currentTimeMillis() - startTime / 1000.0)) + " s");
                }
            });
        }
    }

    private String computeTimeElapsed() {
        double timeElapsed = Math.round((endTime - startTime) / 1000.0);
        return Double.toString(timeElapsed);
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