package com.hotsoup;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class fragment_carbonFootPrintPiechart extends Fragment { //creates a piechart of the data gathered from two different API, one
    View carbonchartview;//                                         of those is ilmastodieetti and the other is triptocarbon.xyz
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    PieChart pieChart;
    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        carbonchartview = inflater.inflate(R.layout.fragment_carbon_foot_print_piechart, container, false);
        return carbonchartview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pieChart = this.carbonchartview.findViewById(R.id.PieChart);
        barChart = this.carbonchartview.findViewById(R.id.CarbonBarChart);
        setupPieChart();
        loadPieChart();
        setupBarchart();
}

    private void setupPieChart() { //Sets up the piechart visuality
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Hiilijalanjälki");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChart(){ //loads values to piechart
        ArrayList<PieEntry> values = new ArrayList<>();
        Double sum = 0.0;
        Double sum2 = 0.0;
        for(int i = 0; i<user.carbonfootprint.size(); i++){ //count the overall carbon footprint by category food or travelling
            sum += user.getCarbonfootprint().get(i);
    }
        for(int i = 0; i<user.travelcarbonfootprint.size(); i++){
            sum2 += user.getTravelcarbonfootprint().get(i);
        }

        values.add(new PieEntry(sum.floatValue(), "Ruoka" ));
        values.add(new PieEntry(sum2.floatValue(), "Matkustus"));



        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) { //colors for the piechart
            colors.add(color);
        }

        PieDataSet data = new PieDataSet(values, "Hiilijalanjälki jaettuna kategorioihin");
        data.setColors(colors);
        PieData d = new PieData(data);

        d.setValueFormatter(new PercentFormatter(pieChart)); // setting up the piechart to show the percentage by gategory
        d.setDrawValues(true);
        d.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(d);
        pieChart.invalidate();

        pieChart.animateY(2000, Easing.EaseInOutQuad);
    }


    public void setupBarchart() {
        ArrayList<BarEntry> travelvalues = new ArrayList<>();
        ArrayList<BarEntry> foodvalues = new ArrayList<>();

        for (int i = 0; i < user.travelcarbonfootprint.size(); i++) {
            travelvalues.add(new BarEntry(i, user.travelcarbonfootprint.get(i).floatValue()));
        }

        for (int i = 0; i < user.carbonfootprint.size(); i++) {
            foodvalues.add(new BarEntry(i, user.carbonfootprint.get(i).floatValue()));
            System.out.println(user.carbonfootprint.get(i).toString());
        }

        BarDataSet brd1 = new BarDataSet(travelvalues, "Matkustus hiilijalanjälki (kg's)");
        brd1.setColor(Color.rgb(104, 241, 175));
        BarDataSet brd2 = new BarDataSet(foodvalues, "Ruokavalintojen hiilijalanjälki (kg's)");
        brd2.setColor(Color.RED);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        dataSets.add(brd1);
        dataSets.add(brd2);


        BarData barData = new BarData(dataSets);

        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        float barSpace = 0.1f;
        float groupSpace = 0.5f;
        barData.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.animate();
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * travelvalues.size());
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }
}