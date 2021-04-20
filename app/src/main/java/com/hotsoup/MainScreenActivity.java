package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

public class MainScreenActivity extends AppCompatActivity {
    BarChart barChart;

    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        System.out.println("##########Main##############");

        barChart = findViewById(R.id.bar_chart_main);

        user.lastActivity = getClass().getName();
        lp.updateUserData(user);

    }

    private void loadChart(){


    }
    //TODO Kun activity on luotu lisää se allaolevaan kohtaan
    public void startFoodMenuActivity(View v){
        Intent myIntent = new Intent(this, foodDataHarvester.class);
        startActivity(myIntent);
        finish();
    }

    public void startFoodDiaryActivity(View v){
        Intent myIntent = new Intent(this, userMealDiary.class);
        startActivity(myIntent);
        finish();
    }

    public void startEditProfileActivity(View v){
        Intent myIntent = new Intent(this, EditProfileActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void startCarbonFootPrintActivity(View v){}


    public void startWeigthActivity(View v){
        Intent myIntent = new Intent(this, WeightTracker.class);
        startActivity(myIntent);
        finish();
    }


    public void startRandomFactsActivity(View v){Intent myIntent = new Intent(this, RandomFactsActivity.class);
        startActivity(myIntent);
        finish();

    }
}