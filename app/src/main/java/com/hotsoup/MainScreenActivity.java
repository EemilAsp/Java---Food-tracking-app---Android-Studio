package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {
    BarChart barChart;
    UserProfile user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Intent intent = getIntent();
        user = (UserProfile) intent.getSerializableExtra("user");
        barChart = findViewById(R.id.bar_chart_main);
    }

    private void loadChart(){
        //TODO ESIMERKKI ARVOT MUUTA NÄÄ
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(3,1, "Kusipäätä"));

    }
}