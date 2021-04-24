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
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class fragment_carbonFootPrintPiechart extends Fragment {
    View carbonchartview;
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        carbonchartview = inflater.inflate(R.layout.fragment_carbon_foot_print_piechart, container, false);
        return carbonchartview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pieChart = this.carbonchartview.findViewById(R.id.PieChart);
        setupPieChart();
        loadPieChart();

}

    private void setupPieChart() { //Sets up the piechart visuality
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChart(){
        ArrayList<PieEntry> values = new ArrayList<>();
        Double sum = 0.0;
        Double sum2 = 0.0;
        for(int i = 0; i<user.carbonfootprint.size(); i++){
            sum += user.getCarbonfootprint().get(i);
    }
        for(int i = 0; i<user.travelcarbonfootprint.size(); i++){
            sum2 += user.getTravelcarbonfootprint().get(i);
        }

        values.add(new PieEntry(sum.floatValue(), "Food choice's"));
        values.add(new PieEntry(sum2.floatValue(), "Travelling"));



        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        PieDataSet data = new PieDataSet(values, "Carbon footprint divided to categories");
        data.setColors(colors);
        PieData d = new PieData(data);

        d.setDrawValues(true);
        d.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(d);
        pieChart.invalidate();

        pieChart.animateY(2000, Easing.EaseInOutQuad);
    }


}