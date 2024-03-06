package com.example.car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car.controller.MainActivityController;

public class MainActivity extends AppCompatActivity{
    private TextView speedTextView, timerTextView;
    private Button resetButton;
    private ProgressBar progressBar;

    private MainActivityController controller;
    
    private static final int locationRequestCode = 1;
    private static final int minimumUpdateTime = 0; // ms
    private static final int minimumUpdateDistance = 0; //m

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

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumUpdateTime, minimumUpdateDistance, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    controller.onLocationChanged((int) (location.getSpeed() * 3.6));
                }
            });
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onResetAction();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.settings){
            controller.startSettingsActivity();
            return true;
        }
        else if (itemId == R.id.statistics){
            controller.startStatisticsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
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