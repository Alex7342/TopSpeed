package com.example.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultsAdaptor extends RecyclerView.Adapter<ResultsAdaptor.MyViewHolder> {
    Context context;
    ArrayList<ResultsModel> resultsModelsList;
    public ResultsAdaptor(Context context, ArrayList<ResultsModel> resultsModelsList){
        this.context = context;
        this.resultsModelsList = resultsModelsList;
    }
    @NonNull
    @Override
    public ResultsAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);

        return new ResultsAdaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdaptor.MyViewHolder holder, int position) {
        holder.totalTime.setText(resultsModelsList.get(position).getTotalTime());
        holder.averageSpeed.setText(resultsModelsList.get(position).getAverageSpeed());
        holder.maxSpeed.setText(resultsModelsList.get(position).getMaxSpeed());
    }

    @Override
    public int getItemCount() {
        return resultsModelsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView totalTime, maxSpeed, averageSpeed;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            totalTime = itemView.findViewById(R.id.totalTimeTextView);
            maxSpeed = itemView.findViewById(R.id.maxSpeedTextView);
            averageSpeed = itemView.findViewById(R.id.averageSpeedTextView);


        }
    }
}
