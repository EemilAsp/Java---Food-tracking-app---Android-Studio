package com.hotsoup;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class userMealDiary extends AppCompatActivity implements RecyclerViewClickInterface { // Purpose of this activity is for the user to see what he/she has eaten at given date
    private AlertDialog.Builder dialogBuilder;
    final static String DATE_FORMAT = "dd.MM.yyyy";
    private AlertDialog dialog;

    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();

    boolean choice = true;
    Toolbar tb;
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

        user.lastActivity = getClass().getName();
        lp.updateUserData(user);

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

        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });
    }

    @Override
    public void recyclerViewListClicked(String food) {
        createEditPopUp(food);
        System.out.println(food);
    }

    private void createEditPopUp(String food) { //Creating a popup when user selects a food from recyclerview(contains the information of added food) this searches the data from diary of selected food
        String date = getDateText();// and sends it forward to the real popup
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

    public void popupNow(String ml, int index, String dt){ // Gets the meal, index in diary and date key, possibility for the user to delete selected food
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
                dialog.dismiss();
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

    public String getDateText(){ // takes the date string from dateinput, includes error handling (For instance if no date added date will be teh current date, if date in letters or input
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT); // is not a date, error will popup,
        Date dateobject = new Date();
        if(dateInsert.getText().toString().isEmpty()){
            return formatter.format(dateobject);
        }else{
            Boolean value = testDateText(dateInsert.getText().toString());
            if (value == true) {
                return dateInsert.getText().toString();
            }else{
                return formatter.format(dateobject);
            }
        }
    }

    public boolean testDateText(String datestr){ // testing if the date is really a date object
        try{
            DateFormat df = new SimpleDateFormat((DATE_FORMAT));
            df.setLenient(false);
            df.parse(datestr);
            return true;
        } catch (ParseException e) {
            ErrorPopUp error = new ErrorPopUp("ERROR", "Wrong date format use dd.MM.yyyy");
            error.show(getSupportFragmentManager(), "ERROR");
            return false;
        }
    }

    public void createStringArray(ArrayList<userMeal> umeals){ // calculates the total nutritional data from selected date
        double totalenergy = 0, totalprotein = 0, totalcarb = 0, totalfat = 0, totalfiber = 0, totalsugar = 0, totalalcohol = 0;
        datesfoodnames.clear();
        datesMeals.clear();
        if(umeals == null){ // if meals not found
            ErrorPopUp error = new ErrorPopUp("ERROR", "No meals for selected date, try something else");
            error.show(getSupportFragmentManager(), "ERROR");
        }else{
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
        daysinfocount = "Total calories: "+df.format(totalenergy)+"kcal"+ //The extras option, showing more indepth info from the dates eating
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
    }}

    public void addedAMealPopup(String extrainfo){ //Creating a popup for extra info
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


    private void goMainScreen(){
        Intent myIntent = new Intent(this, MainScreenActivity.class);
        startActivity(myIntent);
        finish();}
}




