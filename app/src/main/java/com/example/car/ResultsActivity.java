package com.example.car;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

        exitButton = findViewById(R.id.exitButton);
        recyclerView = findViewById(R.id.recyclerView);

        ResultsAdaptor adaptor = new ResultsAdaptor(this, )

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics metrics = windowManager.getMaximumWindowMetrics();
        Rect bounds = metrics.getBounds();

        int width = bounds.width();
        int height = bounds.height();
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));

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