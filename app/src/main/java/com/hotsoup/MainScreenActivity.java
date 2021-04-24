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

    
    public void startFoodMenuActivity(View v){                  //starts the activity in which new meals can be added
        Intent myIntent = new Intent(getApplicationContext(), foodDataHarvester.class);
        startActivity(myIntent);
    }

    public void startEditProfileActivity(View v){               //starts the activity where user profile can be modified
        Intent myIntent = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(myIntent);
    }

    public void startCarbonFootprintActivity(View v){           //starts the activity in which food and travel carbon footprint is calculated with APIs
        Intent intent = new Intent(MainScreenActivity.this, CarbonFootprintActivity.class);
        startActivity(intent);
        finish();
    }


    public void startWeigthActivity(View v){                    //starts the activity which calculates BMI
        Intent myIntent = new Intent(getApplicationContext(), WeightTracker.class);
        startActivity(myIntent);
    }


    public void startRandomFactsActivity(View v){Intent myIntent = new Intent(getApplicationContext(), RandomFactsActivity.class);          //starts the random facts activity which shows interesting quotes
        startActivity(myIntent);

    }
}