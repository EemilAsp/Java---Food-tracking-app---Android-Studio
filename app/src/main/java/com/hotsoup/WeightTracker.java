package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class WeightTracker extends AppCompatActivity { //tracks the users bodyweight
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();

    MaterialButtonToggleGroup buttonGroup;
    Button weightModeButton;
    Button bmiModeButton;
    FrameLayout frameLayout;
    Fragment fragment;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        System.out.println("##########Edit Weight##############");
        buttonGroup = findViewById(R.id.button_toggle_group_weight);
        buttonGroup.check(R.id.see_weight_button);
        weightMode(new View(getBaseContext()));

        user = lp.getUser();
        user.lastActivity = getClass().getName();

        lp.updateUserData(user);
        //Toolbar support Start
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });


    }
    private void goMainScreen(){ // these are for the toolbar to work as an home button, bringing back to mainscreen
            Intent myIntent = new Intent(this, MainScreenActivity.class);
            startActivity(myIntent);
            finish();}
        //ToolbarSupport END

    public void bmiMode(View v){ // calculates users BMI with
        fragment = new BMICalculator();
        FragmentManager manager =  getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout_weight, fragment);
        transaction.commit();

    }
    public void weightMode(View v){
        fragment = new addNewWeight_fragment();
        FragmentManager manager =  getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout_weight, fragment);
        transaction.commit();
    }
}