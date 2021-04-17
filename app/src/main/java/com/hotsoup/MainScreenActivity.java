package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {
    BarChart barChart;
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        System.out.println("##########Main##############");
        Intent intent = getIntent();
        user = (UserProfile) intent.getSerializableExtra("user");
        barChart = findViewById(R.id.bar_chart_main);
        if(user != null){
            //This saves the last activity. copy this to every activity where user is logged in
            user.lastActivity = getClass().getName();
            lp.updateUserData(user);}
    }

    private void loadChart(){
        //TODO ESIMERKKI ARVOT MUUTA NÄÄ
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(3,1, "Kusipäätä"));

    }
    //TODO Kun activity on luotu lisää se allaolevaan kohtaan
    public void startFoodMenuActivity(View v){}
    public void startEditProfileActivity(View v){
        Intent myIntent = new Intent(this, EditProfileActivity.class);
        myIntent.putExtra("user", user);
        startActivity(myIntent);
        finish();
    }
    public void startCarbonFootPrintActivity(View v){}
    public void startWeigthActivity(View v){}
    public void startRandomFactsActivity(View v){}
}