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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class tobacco_barchart extends Fragment {
    View fragmentsview;

    BarChart barchart;
    ArrayList<String> maledata;
    ArrayList<String> femaledata;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentsview = inflater.inflate(R.layout.fragment_barcharttobacco, container, false);
        return fragmentsview;
    }


    @Override
    public void onViewCreated(@NonNull View fragmentsview, @Nullable Bundle savedInstanceState) {
        barchart = this.fragmentsview.findViewById(R.id.barChart);

        maledata = getArguments().getStringArrayList("male");
        femaledata = getArguments().getStringArrayList("female");
        //vastaanota arrayt 2 eri arrayta
        ArrayList<BarEntry> maleyears = new ArrayList<>();
        maleyears.add(new BarEntry( 2017,Float.parseFloat(maledata.get(0))));//Miesten
        maleyears.add(new BarEntry(2018 ,Float.parseFloat(maledata.get(1))));//Naisten
        maleyears.add(new BarEntry(2019 ,Float.parseFloat(maledata.get(2)) ));//Naisten
        ArrayList<BarEntry> femaleyears = new ArrayList<>();
        femaleyears.add(new BarEntry(2017 ,Float.parseFloat(femaledata.get(0)) ));//Naisten
        femaleyears.add(new BarEntry(2018 , Float.parseFloat(femaledata.get(1))));//Naisten
        femaleyears.add(new BarEntry(2019 ,Float.parseFloat(femaledata.get(2))));//Naisten

        BarDataSet bardataset = new BarDataSet(maleyears, "male daily smokers (%)");
        bardataset.setColors(Color.BLUE);
        bardataset.setValueTextColor(Color.BLACK);
        bardataset.setValueTextSize(16f);

        BarDataSet bardataset2 = new BarDataSet(femaleyears, "female daily smokers (%)");
        bardataset2.setColors(Color.RED);
        bardataset2.setValueTextColor(Color.BLACK);
        bardataset2.setValueTextSize(16f);

        BarData bardata = new BarData(bardataset, bardataset2);
        barchart.setFitBars(true);
        barchart.setData(bardata);
        barchart.getDescription().setText("Finnish daily smokers (%)");
        barchart.animateY(2000);
    }
}