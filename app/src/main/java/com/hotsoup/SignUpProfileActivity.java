package com.hotsoup;

import androidx.appcompat.app.AppCompatActivity;

import com.ybs.passwordstrengthmeter.PasswordStrength;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SignUpProfileActivity extends AppCompatActivity implements TextWatcher {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up_profile);

            EditText password = (EditText)findViewById(R.id.login_password);
            password.addTextChangedListener(this);

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
            updatePasswordStrengthView(s.toString());
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
    }
    }

