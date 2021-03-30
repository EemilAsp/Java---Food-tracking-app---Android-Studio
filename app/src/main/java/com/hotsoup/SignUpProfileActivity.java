package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ybs.passwordstrengthmeter.PasswordStrength;

public class SignUpProfileActivity extends AppCompatActivity implements TextWatcher {
    EditText password;
    EditText passwordAgain;
    EditText username;
    ProgressBar progressBar;
    Button buttonMakeProfile;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up_profile);

            password = (EditText)findViewById(R.id.login_password);
            password.addTextChangedListener(this);
            passwordAgain = findViewById(R.id.text_password_again);
            passwordAgain.addTextChangedListener(this);
            progressBar = findViewById(R.id.progressBar);
            buttonMakeProfile = findViewById(R.id.button_signup);
            username = findViewById(R.id.edit_username);


        }

        @Override
        public void afterTextChanged(Editable s) {
        }
        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(getCurrentFocus() == password){updatePasswordStrengthView(s.toString());}
            if(password.getText().toString().equals(passwordAgain.getText().toString()) && progressBar.getProgress()>=3){
                buttonMakeProfile.setEnabled(true);
            }
            else {buttonMakeProfile.setEnabled(false);}
        }


        private void updatePasswordStrengthView(String password) {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            TextView strengthView = (TextView) findViewById(R.id.password_strength);
            if (TextView.VISIBLE != strengthView.getVisibility())
                return;

            if (password.isEmpty()) {
                strengthView.setText("");
                progressBar.setProgress(0);
                return;
            }

            PasswordStrength str = PasswordStrength.calculateStrength(password);
            strengthView.setText(str.getText(this));
            strengthView.setTextColor(str.getColor());

            progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
            if (str.getText(this).equals("Weak")) {
                progressBar.setProgress(25);
            } else if (str.getText(this).equals("Medium")) {
                progressBar.setProgress(50);
            } else if (str.getText(this).equals("Strong")) {
                progressBar.setProgress(75);
            } else {
                progressBar.setProgress(100);
            }
        }
        public void toSingIn(View v){
            Intent myIntent = new Intent(this, SigninProfileActivity.class);
            startActivity(myIntent);
            finish();
        }
        public void makeProfile(View v){
            LoadProfile lp = LoadProfile.getInstance();
            if(lp.isNameFree(username.getText().toString())){
                lp.createNewProfile(username.getText().toString(), password.getText().toString());
                toSingIn(v);
            }
            else{
                buttonMakeProfile.setEnabled(false);
                ErrorPopUp error = new ErrorPopUp("Ongelma tilin luonnissa", "Käyttäjänimi on jo varattu");
                error.show(getSupportFragmentManager(), "error");
                password.setText("");
                passwordAgain.setText("");
                username.setText("");
            }


        }
    }

