package com.example.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdaptor extends BaseAdapter {
    Context context;
    ArrayList<Result> resultArrayList;
    LayoutInflater inflater;

    public ResultAdaptor(Context context, ArrayList<Result> resultArrayList){
        this.context = context;
        this.resultArrayList = resultArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resultArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list_view, null);
        TextView totalTimeTextView = (TextView) convertView.findViewById(R.id.totalTimeTextView);
        TextView maxSpeedTextView = (TextView) convertView.findViewById(R.id.maxSpeedTextView);
        TextView averageSpeedTextView = (TextView) convertView.findViewById(R.id.averageSpeedTextView);
        TextView testTimeTextView = (TextView) convertView.findViewById(R.id.testTimeTextView);

        totalTimeTextView.setText("Total " + Double.toString(resultArrayList.get(position).getTime_0_100()) + " s");
        maxSpeedTextView.setText("Max " + Integer.toString(resultArrayList.get(position).getMaxSpeed()) + " km/h");
        averageSpeedTextView.setText("Avg " + Double.toString(resultArrayList.get(position).getAverageSpeed()) + " km/h");
        testTimeTextView.setText(resultArrayList.get(position).getDateTime());

        return convertView;
    }
}
