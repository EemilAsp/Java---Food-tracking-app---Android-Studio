package com.hotsoup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class WeightTracker extends AppCompatActivity {
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();

    MaterialButtonToggleGroup buttonGroup;
    Button weightModeButton;
    Button bmiModeButton;
    LinearLayout linearLayout;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        System.out.println("##########Edit Weight##############");
        user = lp.getUser();
        user.lastActivity = getClass().getName();
        lp.updateUserData(user);

        buttonGroup = findViewById(R.id.button_toggle_group_weight);
        weightModeButton = findViewById(R.id.see_weight_button);
        bmiModeButton = findViewById(R.id.see_bmi_weight);
        linearLayout  = findViewById(R.id.linear_layout_weight);
        frameLayout = findViewById(R.id.frame_layout_weight);

        buttonGroup.check(R.id.see_weight_button);
    }


    public void bmiMode(View v){
        linearLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

    }
    public void weightMode(View v){
        linearLayout.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }


    public void saveWeight(View v){}
    public void removeLastWeight(View v){}
    public void backToMain(View v){}
}