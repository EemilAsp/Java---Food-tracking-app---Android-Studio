package com.hotsoup;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class userMealDiary extends AppCompatActivity implements RecyclerViewClickInterface {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    boolean choice = true;
    DecimalFormat df = new DecimalFormat("#.#");
    String daysinfocount;
    userFoodDiary userfooddiary = userFoodDiary.getInstance();
    RecyclerView datefoodview;
    Button returnButton, searchButton, extraButton;
    TextView mealPopup;
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
        extraButton = findViewById(R.id.Extras);
        extraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedAMealPopup(daysinfocount);
            }
        });
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
        createEditPopUp(food);
        System.out.println(food);
    }

    private void createEditPopUp(String food) {
        String date = getDateText();
        daysEats = userfooddiary.getArray(date);
        for(int i = 0; i<daysEats.size(); i++){
            userMeal um = daysEats.get(i);
            if(um.getFoodname().equals(food)){
                String meal = um.getFoodname()+"" +
                        "\nPortion: "+df.format(um.getPortionsize())+"g"+
                        "\nCalories: "+df.format(um.getEnergy())+"kcal"+
                        "\nfats: "+df.format(um.getFats())+"g" +
                        "\nProtein: "+df.format(um.getProtein())+"g" +
                        "\nCarbohydrates: "+df.format(um.getCarb())+"g" +
                        "\n     Dietary fiber: "+df.format(um.getFiber())+"g" +
                        "\n     Sugar: "+df.format(um.getSugar())+"g"+
                        "\nAlcohol: "+df.format(um.getAlcohol())+"g";
                popupNow(meal, i, date);
            }
        }
    }

    public void popupNow(String ml, int index, String dt){
        dialogBuilder = new AlertDialog.Builder(this);
        final View editMealPopup = getLayoutInflater().inflate(R.layout.chosenfoodedit, null);
        Button returnbutton = (Button) editMealPopup.findViewById(R.id.chosenFoodReturn);
        Button removebutton = (Button) editMealPopup.findViewById(R.id.chosenFoodDelete);
        TextView fooditem = (TextView) editMealPopup.findViewById(R.id.editSelectedMeal);
        fooditem.setText(ml);
        dialogBuilder.setView(editMealPopup);
        dialog = dialogBuilder.create();
        dialog.show();
        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userfooddiary.getArray(dt).remove(index);
                System.out.println("Poistettu");
            }
        });
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButton.performClick();
                dialog.dismiss();
            }
        });
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
        double totalenergy = 0, totalprotein = 0, totalcarb = 0, totalfat = 0, totalfiber = 0, totalsugar = 0, totalalcohol = 0;
        datesfoodnames.clear();
        datesMeals.clear();
        for(int i = 0; i < umeals.size(); i++){
            userMeal um = umeals.get(i);
            datesfoodnames.add(um.getFoodname());
            totalenergy += um.getEnergy();
            totalalcohol += um.getAlcohol();
            totalcarb += um.getCarb();
            totalfat += um.getFats();
            totalfiber += um.getFiber();
            totalsugar += um.getSugar();
            totalprotein += um.getProtein();
        }
        daysinfocount = "Total calories: "+df.format(totalenergy)+"kcal"+
                "\nProtein: "+df.format(totalprotein)+"g"+
                "\nFats: "+df.format(totalfat)+"g"+
                "\nCarbs: "+df.format(totalcarb)+"g"+
                "\nDietary fiber: "+df.format(totalfiber)+"g"+
                "\nSugar: "+df.format(totalsugar)+"g"+
                "\nAlcohol: "+df.format(totalalcohol)+"g";
        String infoforlist = "Total calories: "+df.format(totalenergy)+"kcal"+
                "\nProtein: "+df.format(totalprotein)+"g Fats: "+df.format(totalfat)+"g"+
                "\nCarbs: "+df.format(totalcarb)+"g";
        datesfoodnames.add(0, infoforlist);
        rVadapter = new RVadapter(this, datesfoodnames, this::recyclerViewListClicked);
        rVadapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        datefoodview.setLayoutManager(layoutManager);
        datefoodview.setItemAnimator(new DefaultItemAnimator());
        datefoodview.setAdapter(rVadapter);
    }

    public void addedAMealPopup(String extrainfo){
        Thread thread = new Thread();
        dialogBuilder = new AlertDialog.Builder(this);
        final View addedmealPopup = getLayoutInflater().inflate(R.layout.dialog, null);
        TextView header = addedmealPopup.findViewById(R.id.addedAMeal);
        header.setText("Dates nutrition information");
        mealPopup = (TextView) addedmealPopup.findViewById(R.id.mealPopUP);
        mealPopup.setTextSize(24);
        mealPopup.setText(extrainfo);
        dialogBuilder.setView(addedmealPopup);
        dialog = dialogBuilder.create();
        dialog.show();
        addedmealPopup.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);

    }
}




