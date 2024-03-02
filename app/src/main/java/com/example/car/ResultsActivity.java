package com.example.car;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class ResultsActivity extends AppCompatActivity {

    private Button exitButton;

    private CardView cardView;
    private TextView totalTimeTextView;
    private TextView averageSpeedTextView;
    private TextView maxSpeedTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();

        exitButton = findViewById(R.id.exitButton);
        cardView = findViewById(R.id.cardView);
        totalTimeTextView = cardView.findViewById(R.id.totalTimeTextView);
        averageSpeedTextView = cardView.findViewById(R.id.averageSpeedTextView);
        maxSpeedTextView = cardView.findViewById(R.id.maxSpeedTextView);

        totalTimeTextView.setText(intent.getStringExtra("totalTime"));
        averageSpeedTextView.setText(intent.getStringExtra("averageSpeed"));
        maxSpeedTextView.setText(intent.getStringExtra("maxSpeed"));

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}