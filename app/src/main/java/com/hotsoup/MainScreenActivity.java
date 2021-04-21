package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreenActivity extends AppCompatActivity {

    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        System.out.println("##########Main##############");


        user.lastActivity = getClass().getName();
        lp.updateUserData(user);

    }

    
    public void startFoodMenuActivity(View v){
        Intent myIntent = new Intent(getApplicationContext(), foodDataHarvester.class);
        startActivity(myIntent);
    }

    public void startEditProfileActivity(View v){
        Intent myIntent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(myIntent);
    }

    public void startCarbonFootPrintActivity(View v){}


    public void startWeigthActivity(View v){
        Intent myIntent = new Intent(getApplicationContext(), WeightTracker.class);
        startActivity(myIntent);
    }


    public void startRandomFactsActivity(View v){Intent myIntent = new Intent(getApplicationContext(), RandomFactsActivity.class);
        startActivity(myIntent);

    }
}