package com.hotsoup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class addNewWeight_fragment extends Fragment {
    UserProfile user;
    LoadProfile lp = LoadProfile.getInstance();
    Button saveWeight;
    Button removeWeight;



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

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };


        return view;
    }
    public void addWeight(View v){}
}