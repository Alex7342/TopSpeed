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

public class MainActivity extends AppCompatActivity{
    private TextView speedTextView;
    private ProgressBar progressBar;

    private Repository repository;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime = 0; // ms
    private static final int minimumUpdateDistance = 0; //m

    private void initialise() {
        repository = new Repository();

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
        startActivity(new Intent(MainActivity.this, ResultsActivity.class));
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        speedTextView = findViewById(R.id.texViewProgress);
        
        requestPermissions();
        initialise();
        startResultsActivity();
        
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumUpdateTime, minimumUpdateDistance, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    int speed = (int) (location.getSpeed() * 3.6);
                    speedTextView.setText(speed + " km/h");
                    progressBar.setProgress(speed);

                    if (!repository.getHasChanged() && speed > 0) {
                        repository.setHasChanged(true);
                        repository.setStartTime(System.currentTimeMillis());
                    }

                    if (speed >= 100 && !repository.getHasFinished()) {
                        repository.setHasFinished(true);
                        repository.setEndTime(System.currentTimeMillis());
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