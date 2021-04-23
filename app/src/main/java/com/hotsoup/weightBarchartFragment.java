package com.hotsoup;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class weightBarchartFragment extends Fragment {
    View chartview;
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        chartview = inflater.inflate(R.layout.fragment_weight_barchart, container, false);
        return chartview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BarChart barchart = this.chartview.findViewById(R.id.barchart);


        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i = 0; i < user.getWeight().size(); i++){
            values.add(new BarEntry(i, user.getWeight().get(i).floatValue()));
        }

        BarDataSet barDataSet = new BarDataSet(values, "Käyttäjän painon kehitys");
        barDataSet.setColors(Color.CYAN);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setBarBorderWidth(2);
        barDataSet.setValueTextSize(22f);


        BarData bardata = new BarData(barDataSet);
        barchart.setFitBars(true);
        barchart.setData(bardata);
        barchart.setMinimumWidth(2);
        barchart.setScaleEnabled(false);
        barchart.animateY(2000);

    }
}