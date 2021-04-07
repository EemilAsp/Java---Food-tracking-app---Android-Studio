package com.hotsoup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();

    Button editProfile;
    Button seeProfile;
    MaterialButtonToggleGroup buttonGroup;
    Spinner spinner;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        System.out.println("##########Edit Profile##############");
        Intent intent = getIntent();
        user = (UserProfile) intent.getSerializableExtra("user");
        if(user != null){
            //This saves the last activity
            user.lastActivity = getClass().getName();
            lp.updateUserData(user);}

       editProfile = findViewById(R.id.edit_profile_button);
       seeProfile = findViewById(R.id.see_profile_button);
       buttonGroup = findViewById(R.id.button_toggle_group);
       spinner = findViewById(R.id.spinner_menu);


       //Sets default value
       buttonGroup.check(R.id.see_profile_button);

    }
    public void changeBirthDate(View v){
        DialogFragment datepicker = new DatePickerFragment(user.getYearOfBirth());
        datepicker.show(getSupportFragmentManager(), "change birthday");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        user.setYearOfBirth(calendar);
    }


    public void logOutOfProfile(View v){
        user.setRememberMe(false);
        user.setLastActivity(MainScreenActivity.class.getName());
        lp.updateUserData(user);

        startActivity(new Intent(this, MainActivity.class));

    }



}
