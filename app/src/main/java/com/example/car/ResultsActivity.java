package com.example.car;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

import javax.xml.transform.Result;

public class ResultsActivity extends AppCompatActivity {

    private Button exitButton;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();

        exitButton = findViewById(R.id.exitButton);
        recyclerView = findViewById(R.id.recyclerView);

        ResultsModel resultModel = new ResultsModel(intent.getStringExtra("totalTime"),
                intent.getStringExtra("averageSpeed"), intent.getStringExtra("maxSpeed"));

        ArrayList<ResultsModel> resultsModelsList = new ArrayList<>();
        resultsModelsList.add(resultModel);

        ResultsAdaptor adaptor = new ResultsAdaptor(this, resultsModelsList);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics metrics = windowManager.getMaximumWindowMetrics();
        Rect bounds = metrics.getBounds();

        int width = bounds.width();
        int height = bounds.height();
        //getWindow().setLayout((int) (width * 0.4), (int) (height * 0.4));

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private ArrayList<ResultsModel> getResultsModelArrayList(ArrayList<Result> inputList){
        ArrayList<ResultsModel> resultArray = new ArrayList<>();
        return resultArray;
    }
}