package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SigninProfileActivity extends AppCompatActivity {
    TextInputEditText username;
    TextInputEditText password;
    CheckBox checkBox;
    Button login;
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_profile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        password = findViewById(R.id.password_text);
        username = findViewById(R.id.text_username);
        checkBox = findViewById(R.id.remember_check);
        login = findViewById(R.id.button_signin);

        lp.reload();
        if((user = lp.findloggedIn()) != null){
            Intent myIntent = new Intent(this, user.getLastintent().getClass());
            myIntent.putExtra("user", user);
            startActivity(myIntent);
        }

    }



    public void signinToProfile(View v){
        user = lp.identifyUser(username.getText().toString(), password.getText().toString());
        System.out.println(user);
        if(user == null){
            ErrorPopUp error = new ErrorPopUp("Ongelma tunnistautumisessa", "Käyttäjänimi tai salasana on väärin");
            error.show(getSupportFragmentManager(), "error");
            username.setText("");
            password.setText("");
        }
        else {
            if(checkBox.isChecked()){
                user.setRememberMe(true);
            }
            Intent myIntent = new Intent(this, MainScreenActivity.class);
            myIntent.putExtra("user", user);
            startActivity(myIntent);
            finish();
        }

    }


    public void toSingUp(View v){
        Intent myIntent = new Intent(this, SignUpProfileActivity.class);
        startActivity(myIntent);
        finish();
    }


}