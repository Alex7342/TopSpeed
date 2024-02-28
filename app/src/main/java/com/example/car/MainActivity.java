package com.example.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView speedTextView;
    private TextView resultTextView;
    private SeekBar speedSeekBar;
    private long startTime;
    private long endTime;
    private boolean hasChanged;
    private boolean hasFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedTextView = findViewById(R.id.speedTextView);
        resultTextView = findViewById(R.id.resultTextView);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        hasChanged = false;
        hasFinished = false;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, this);
        this.onLocationChanged((Location) null);
    }

    private String computeTimeElapsed() {
        double timeElapsed = (double) ((endTime - startTime) / 1000.0);
        return Double.toString(timeElapsed);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            speedTextView.setText("0");
            speedSeekBar.setProgress(0);
        }
        else {
            int speed = (int) ((location.getSpeed() * 3600) / 1000);
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


    }
}