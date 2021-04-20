package com.hotsoup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BMICalculator extends Fragment {

    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    double bmi;


    TextView textWeight;
    TextView textHeight;
    TextView textBmi;
    TextView textInfo;


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b_m_i_calculator, container, false);
        textWeight = view.findViewById(R.id.text_weight_bmi);
        textHeight = view.findViewById(R.id.text_height_bmi);
        textBmi = view.findViewById(R.id.text_bmi_bmi);
        textInfo = view.findViewById(R.id.text_info_bmi);
        if(!user.getWeight().isEmpty() && user.getHeight() !=0){
            textWeight.setText(getString(R.string.your_last_weight) + user.getWeight().get(user.getWeight().size()-1));
            textHeight.setText(getString(R.string.height_is) +user.getHeight());
            textBmi.setText(getString(R.string.calculated_bmi) + Double.toString(
                    bmi = calculateBMI(user.getWeight().get(user.getWeight().size()-1), user.getHeight())));
            if(bmi<18.5){textInfo.setText(R.string.bmi_says + getString(R.string.underWeight));}
            else if(18.5<= bmi && bmi< 25){textInfo.setText(R.string.bmi_says +getString(R.string.normal_weight));}
            else if(25<= bmi && bmi< 30){textInfo.setText(R.string.bmi_says + getString(R.string.overweight));}
            else if(30<= bmi && bmi<= 35){textInfo.setText(R.string.bmi_says +getString(R.string.obese));}
            else {textInfo.setText(R.string.bmi_says +getString(R.string.extremly_obese));}}

        else{textWeight.setText(R.string.give_weight_height);
                textInfo.setText("");
                textBmi.setText("");
                textHeight.setText("");}

        return view;
    }


    private double calculateBMI(double weight, double height){
        return weight/(height/100*height/100);
    }
}