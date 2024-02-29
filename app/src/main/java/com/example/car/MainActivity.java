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

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView speedTextView;
    private TextView resultTextView;
    private SeekBar speedSeekBar;
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime= 80; // ms
    private static final int minimumUpdateDistance = 3; //m

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
        hasChanged = false;
        hasFinished = false;
        
        requestPermissions();
        
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minimumUpdateTime, minimumUpdateDistance, this);
        }
    }

    private String computeTimeElapsed() {
        double timeElapsed = ((endTime - startTime) / 1000.0);
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

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            speedTextView.setText("0");
            speedSeekBar.setProgress(0);
        }
        else {
            int speed = (int) (location.getSpeed() * 3.6);
            speedTextView.setText(Integer.toString(speed) + " km/h");
            speedSeekBar.setProgress(speed);

            if (!hasChanged) {
                hasChanged = true;
                startTime = System.currentTimeMillis();
            }

            if (speed >= 100 && !hasFinished) {
                hasFinished = true;
                endTime = System.currentTimeMillis();
                resultTextView.setText("Result: " + computeTimeElapsed() + "seconds");
            }
        }
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onProviderDisabled(String provider) {
        //TODO
    }
}