package com.hotsoup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();

    Button logOut;
    Button editProfile;
    Button seeProfile;
    MaterialButtonToggleGroup buttonGroup;


    TextInputEditText homeTown;
    TextInputEditText height;
    TextInputEditText birth;
    TextView hint;
    Toolbar toolbar;


    Calendar calendar = Calendar.getInstance();


    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        System.out.println("##########Edit Profile##############");
        user = lp.getUser();
        user.lastActivity = getClass().getName();
        lp.updateUserData(user);


       editProfile = findViewById(R.id.edit_profile_button);
       seeProfile = findViewById(R.id.see_profile_button);
       buttonGroup = findViewById(R.id.button_toggle_group);

        homeTown = findViewById(R.id.hometown_edit_text);
        height = findViewById(R.id.height_edit_text);
        birth = findViewById(R.id.birth_edit_text);
        hint = findViewById(R.id.hint_text);



        logOut = findViewById(R.id.log_out_button);

        if(user.getHeight() != 0){height.setText(Integer.toString((int)user.getHeight())); }
        if(user.getHomeCity() != null){homeTown.setText(user.getHomeCity());}
        if(user.getYearOfBirth() != null){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            birth.setText(sdf.format(user.getYearOfBirth().getTime()));
        }




       //Sets default value
       buttonGroup.check(R.id.see_profile_button);

        //toolbar support////////////////////
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
    private void goMainScreen(){
        Intent myIntent = new Intent(this, MainScreenActivity.class);
        startActivity(myIntent);
        finish();}
    /////////////////

    public void changeBirthDate(View v){
        DialogFragment datepicker = new DatePickerFragment(user.getYearOfBirth());
        datepicker.show(getSupportFragmentManager(), "change birthday");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

    }


    public void logOutOfProfile(View v){
        user.setRememberMe(false);
        user.setLastActivity(MainScreenActivity.class.getName());
        lp.updateUserData(user);
        lp.userLoggedOut();
        startActivity(new Intent(this, MainActivity.class));


    }
    //In edit mode
    public void editMode(View v){

        homeTown.setEnabled(true);
        height.setEnabled(true);
        birth.setEnabled(true);
        hint.setVisibility(View.VISIBLE);
        logOut.setEnabled(false);



    }
    public void seeMode(View v){
        homeTown.setEnabled(false);
        height.setEnabled(false);
        birth.setEnabled(false);
        hint.setVisibility(View.GONE);
        logOut.setEnabled(true);

        user.setYearOfBirth(calendar);
        if(height.getText() != null){
        user.setHeight(Integer.parseInt(height.getText().toString()));}

        if(homeTown.getText() != null){
            user.setHomeCity(homeTown.getText().toString());}

        if(user.getYearOfBirth() != null){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            birth.setText(sdf.format(user.getYearOfBirth().getTime()));
        }

        lp.updateUserData(user);
    }



}
