package com.hotsoup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class addNewWeight_fragment extends Fragment implements View.OnClickListener {

    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    Button saveWeight;
    Button removeWeight;
    TextInputEditText weightText;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_weight, container, false);
        saveWeight =view.findViewById(R.id.button_save_weight);
        removeWeight = view.findViewById(R.id.button_remove_weight);
        weightText = view.findViewById(R.id.add_weight_text);

        saveWeight.setOnClickListener(this);
        removeWeight.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save_weight){
                System.out.println("SAVE BUTTON");
                saveWeightUser();
                senddatatoChart();


        }
        else if (v.getId() == R.id.button_remove_weight){
                System.out.println("REMOVE BUTTON");
                removeWeightUser();


        }
    }

    private void senddatatoChart() {
        Fragment frag = new weightBarchartFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.barchartView, frag);
        transaction.commit();
    }

    private void saveWeightUser(){
        System.out.println(weightText.toString());
        try{user.addWeight(Double.parseDouble(weightText.getText().toString()));
            System.out.println(user.getWeight());
            weightText.setText("");}
        catch (NullPointerException | NumberFormatException e){
            Log.getStackTraceString(e);
            System.out.println("#####PARSE EXCEPTON########");
        }
        finally {System.out.println(weightText.getText().toString());

        }

    }
    private void removeWeightUser(){
        ArrayList<Double> copyList = user.getWeight();
        if(!copyList.isEmpty()){
            copyList.remove(copyList.size()-1);
        }
    }

}