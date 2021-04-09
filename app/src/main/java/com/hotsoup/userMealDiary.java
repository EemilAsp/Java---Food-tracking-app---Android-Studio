package com.hotsoup;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class userMealDiary extends AppCompatActivity implements RecyclerViewClickInterface {
    userFoodDiary userfooddiary = userFoodDiary.getInstance();
    RecyclerView datefoodview;
    Button returnButton, searchButton;
    TextView fat, protein, calories, fiber, alcohol, carbs, sugar;
    EditText dateInsert;
    String date;
    RVadapter rVadapter;
    ArrayList<userMeal> daysEats;
    ArrayList<String> datesMeals = new ArrayList<String>();
    ArrayList<String> datesfoodnames = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermealdiarylayout);
        datefoodview = (RecyclerView) findViewById(R.id.dailyMealView);
        searchButton = findViewById(R.id.searchButton);
        returnButton = findViewById(R.id.returnbutton);
        dateInsert = findViewById(R.id.searchByDate);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getDateText();
                daysEats = userfooddiary.getArray(date);
                createStringArray(daysEats);
            }
        });
    }

    @Override
    public void recyclerViewListClicked(String food) {

    }

    public String getDateText(){
        if(dateInsert.getText().toString().isEmpty()){
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date dateobject = new Date();
            String datestring = formatter.format(dateobject).toString();
            return datestring;
        }else{
            String datestring = dateInsert.getText().toString();
            return datestring;
        }
    }

    public void createStringArray(ArrayList<userMeal> umeals){
        DecimalFormat df = new DecimalFormat("#.#");
        double totalenergy = 0, totalprotein = 0, totalcarb = 0, totalfat = 0, totalfiber = 0, totalsugar = 0, totalalcohol = 0;
        datesfoodnames.clear();
        datesMeals.clear();
        for(int i = 0; i < umeals.size(); i++){
            userMeal um = umeals.get(i);
            datesfoodnames.add(um.getFoodname()+" "+df.format(um.getPortionsize())+"g");
            totalenergy += um.getEnergy();
            totalalcohol += um.getAlcohol();
            totalcarb += um.getCarb();
            totalfat += um.getFats();
            totalfiber += um.getFiber();
            totalsugar += um.getSugar();
            totalprotein += um.getProtein();
            String meal = um.getFoodname()+"" +
                    "\nPortion: "+df.format(um.getPortionsize())+"g"+
                    "\nCalories: "+df.format(um.getEnergy())+"kcal"+
                    "\nfats: "+df.format(um.getFats())+"g" +
                    "\nProtein: "+df.format(um.getProtein())+"g" +
                    "\nCarbohydrates: "+df.format(um.getCarb())+"g" +
                    "\n     Dietary fiber: "+df.format(um.getFiber())+"g" +
                    "\n     Sugar: "+df.format(um.getSugar())+"g"+
                    "\nAlcohol: "+df.format(um.getAlcohol())+"g";
            datesMeals.add(meal);
        }
        String daysinfocount = "Total calories: "+df.format(totalenergy)+"kcal"+
                "\nProtein: "+df.format(totalprotein)+"g Fats: "+df.format(totalfat)+"g"+
                "\nCarbs: "+df.format(totalcarb)+"g"+
                "\n Dietary fiber: "+df.format(totalfiber)+"g"+
                "\n Sugar: "+df.format(totalsugar)+"g"+
                "\nAlcohol: "+df.format(totalalcohol)+"g";
        datesfoodnames.add(0, daysinfocount);

        rVadapter = new RVadapter(this, datesfoodnames, this::recyclerViewListClicked);
        rVadapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        datefoodview.setLayoutManager(layoutManager);
        datefoodview.setItemAnimator(new DefaultItemAnimator());
        datefoodview.setAdapter(rVadapter);

    }
}




