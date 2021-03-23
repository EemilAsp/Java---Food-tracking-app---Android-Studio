package com.hotsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SigninProfileActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    CheckBox checkBox;
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_profile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        password = findViewById(R.id.password_text);
        username = findViewById(R.id.edit_username);
        checkBox = findViewById(R.id.remember_check);
        if((user = lp.findloggedIn()) != null){
            Intent myIntent = new Intent(this, MainScreenActivity.class);
            myIntent.putExtra("user", user);
            startActivity(myIntent);
        }

    }



    public void signinToProfile(View v){
        if((user = lp.identifyUser(username.getText().toString(), password.getText().toString())) == null){
            ErrorPopUp error = new ErrorPopUp("Ongelma tunnistautumisessa", "Käyttäjänimi tai salasana on väärin");
            error.show(getSupportFragmentManager(), "error");
        }
        else {
            if(checkBox.isChecked()){
                user.setRememberMe(true);
            }
            Intent myIntent = new Intent(this, MainScreenActivity.class);
            myIntent.putExtra("user", user);
            startActivity(myIntent);
        }

    }


    public void toSingUp(View v){
        Intent myIntent = new Intent(this, SignUpProfileActivity.class);
        startActivity(myIntent);
    }
}