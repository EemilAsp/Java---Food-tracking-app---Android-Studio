package com.hotsoup;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

//Purpose is to get food data from Fineli api, and display it for the user
//User chooses wanted foods and adds those to his/her meal diary
//User can also create own meals, inserting the data in the create own meal
//Could have done this with fragments, because the userMealDiary is almost identical
//Activity might cause lagg, since its a little hard for the RAM

public class foodDataHarvester extends AppCompatActivity implements RecyclerViewClickInterface {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    Toolbar toolbar;
    final static String DATE_FORMAT = "dd.MM.yyyy";
    EditText foodNameComesHere, dateComesHere, portionSizeComesHere;
    RVadapter myAdapter;
    TextView mealPopup, listviewheader;
    String food;
    Button showaddedMeals, createCustomMeal;
    RecyclerView foodview;
    userFoodDiary userfooddiary = userFoodDiary.getInstance();
    ArrayList<String> foodinfo = new ArrayList<>();
    ArrayList<String> usersMealInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddataharvester);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listviewheader = (TextView) findViewById(R.id.listViewHeader);
        portionSizeComesHere = (EditText) findViewById(R.id.portionSizeHere);
        foodNameComesHere = (EditText) findViewById(R.id.foodTextComesHere);
        dateComesHere = (EditText) findViewById(R.id.dateComesHere);
        foodview = (RecyclerView) findViewById(R.id.foodsParsedFromAPI);
        createCustomMeal = (Button)findViewById(R.id.createCustomMeal);
        showaddedMeals = (Button)findViewById(R.id.showaddedMeals);
        toolbar = (Toolbar)findViewById(R.id.include);

        showaddedMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(foodDataHarvester.this, userMealDiary.class);
                startActivityForResult(intent, 1);
            }
        });

        createCustomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOwnMealPopup();
            }
        });



                foodNameComesHere.addTextChangedListener(new TextWatcher() { // searching food items while the search changes
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        food = s.toString();
                        readJSON(food, true);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });
    }

    private void goMainScreen(){
        Intent myIntent = new Intent(this, MainScreenActivity.class);
        startActivity(myIntent);
        finish();}


    public void readJSON(String food, Boolean choice) { //boolean checks if the method call comes from textchange or user clicking element on screen
        String json = getJSON(food); //getting the json for wanted search result
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                String info;
                if (choice == true) {
                    foodinfo.clear();//Parsing the json and showing the names of food items for the user
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        info = jsonObject.getJSONObject("name").getString("fi");
                        foodinfo.add(info);
                    }
                    myAdapter = new RVadapter(this, foodinfo, this::recyclerViewListClicked);
                    myAdapter.notifyDataSetChanged();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    foodview.setLayoutManager(layoutManager);
                    foodview.setItemAnimator(new DefaultItemAnimator());
                    foodview.setAdapter(myAdapter);
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if ((jsonObject.getJSONObject("name").getString("fi")).equals(food)) {
                            String name = food;
                            String date = getDateText();
                            if(date.equals("VIRHE")){
                                ErrorPopUp error = new ErrorPopUp("ERROR", "Wrong date format use dd.MM.yyyy");
                                error.show(getSupportFragmentManager(), "ERROR");
                            }else{
                            double portionsize = getPortionSize();
                            if(portionsize == 666.66666){
                            }else{
                            double fats = Double.parseDouble(jsonObject.getString("fat")) * portionsize/100;
                            double protein = Double.parseDouble(jsonObject.getString("protein")) * portionsize/100;
                            double carb = Double.parseDouble(jsonObject.getString("carbohydrate")) * portionsize/100;
                            double kcal = Double.parseDouble(jsonObject.getString("energyKcal")) * portionsize/100;
                            double alcohol = Double.parseDouble(jsonObject.getString("alcohol")) * portionsize/100;
                            double fiber = Double.parseDouble(jsonObject.getString("fiber")) * portionsize/100;
                            double sugar = Double.parseDouble(jsonObject.getString("sugar")) * portionsize/100;
                            userfooddiary.addMeals(date, name, kcal, portionsize, protein, carb, fats, alcohol, fiber, sugar);
                            System.out.println("DONE");
                            System.out.println(name);
                            System.out.println();
                            System.out.println(portionsize);
                            addedAMealPopup(name);
                        }}}
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

        public String getDateText(){ // getting the date input, and checking if the date is a date or letters
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            Date dateobject = new Date();
            if(dateComesHere.getText().toString().isEmpty()){
                String datestring = formatter.format(dateobject);
                return datestring;
            }else{
                String datestring = dateComesHere.getText().toString();
                Boolean value = testDateText(datestring);
                if (value == true) {
                    return datestring;
                }else{
                    datestring = "VIRHE";
                    return datestring;
                }
            }
        }
    public boolean testDateText(String datestr){ // testing date input
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

    public Double getPortionSize(){ // testing the portionsize input, requires numerical
        double pts;
        if(portionSizeComesHere.getText().toString().isEmpty()){
            pts = 100.0;
            return pts;
        }else{
            if(testPortionSize() == true){
            pts = Double.parseDouble(portionSizeComesHere.getText().toString());
            return pts;
        }else{
            return 666.66666;
        }
    }
    }


    public String getJSON(String food) { // the api harvester with given string
        String response = null;
        try {
            URL url = new URL("https://fineli.fi/fineli/api/v1/foods?q=" + food); //Food parameter from input
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public String objectToString(String foodname){ // searches the clicked food item and shows the additional data with popup
        DecimalFormat df = new DecimalFormat("#.#");
        usersMealInfo.clear();
        String meal = null;
        String date = getDateText();
        if(date.equals("VIRHE")){
            ErrorPopUp error = new ErrorPopUp("ERROR", "Wrong date format use dd.MM.yyyy");
            error.show(getSupportFragmentManager(), "ERROR");
        }else{
        ArrayList<userMeal> umeals = userfooddiary.getArray(date);
        for(int i = 0; i<umeals.size(); i++){
            userMeal um = umeals.get(i);
            if(um.getFoodname().equals(foodname)){
            meal = um.getFoodname()+"" +
                    "\nPortion: "+df.format(um.getPortionsize())+"g"+
                    "\nCalories: "+df.format(um.getEnergy())+"kcal"+
                    "\nfats: "+df.format(um.getFats())+"g" +
                    "\nProtein: "+df.format(um.getProtein())+"g" +
                    "\nCarbohydrates: "+df.format(um.getCarb())+"g" +
                    "\n     Dietary fiber: "+df.format(um.getFiber())+"g" +
                    "\n     Sugar: "+df.format(um.getSugar())+"g"+
                    "\nAlcohol: "+df.format(um.getAlcohol())+"g";
            return meal;
        }}}
        return meal;
    }

    @Override
    public void onActivityResult(int requestcode, int resultCode, Intent data){
        super.onActivityResult(resultCode, resultCode, data);
        String food = data.getStringExtra("foodname");
        readJSON(food, false);
    }


    @Override
    public void recyclerViewListClicked(String food) {//getting the foodname with a message from RV adapter
        System.out.println(food);
        readJSON(food, false);
    }


    public void addedAMealPopup(String food){//meal popup, when the food item is clicked
        Thread thread = new Thread();
        dialogBuilder = new AlertDialog.Builder(this);
        final View addedmealPopup = getLayoutInflater().inflate(R.layout.dialog, null);
        mealPopup = (TextView) addedmealPopup.findViewById(R.id.mealPopUP);
        String fooddata = objectToString(food);
        mealPopup.setText(fooddata);
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

    public void createOwnMealPopup(){ // The popup for the user to create own food item
        dialogBuilder = new AlertDialog.Builder(this);
        final View selfmadeMealPopup = getLayoutInflater().inflate(R.layout.dialog_createownmeal, null);
        EditText proteinValue = (EditText) selfmadeMealPopup.findViewById(R.id.proteinHere);
        EditText nameValue = (EditText) selfmadeMealPopup.findViewById(R.id.foodNameHere);
        EditText carbValue = (EditText) selfmadeMealPopup.findViewById(R.id.carbsHere);
        EditText fiberValue = (EditText) selfmadeMealPopup.findViewById(R.id.fiberHere);
        EditText alcoholValue = (EditText) selfmadeMealPopup.findViewById(R.id.alcoholHere);
        EditText fatsValue = (EditText) selfmadeMealPopup.findViewById(R.id.fatsHere);
        EditText sugarValue = (EditText) selfmadeMealPopup.findViewById(R.id.sugarHere);
        EditText portionSize = (EditText) selfmadeMealPopup.findViewById(R.id.portionHere);
        EditText dateValue = (EditText) selfmadeMealPopup.findViewById(R.id.dateHere);
        Button save = (Button) selfmadeMealPopup.findViewById(R.id.saveButton);
        Button cancel = (Button) selfmadeMealPopup.findViewById(R.id.cancel);

        dialogBuilder.setView(selfmadeMealPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View selfmadeMealPopup) { // if user saves the food item createed
                try{
                String date = dateValue.getText().toString();
                String name = nameValue.getText().toString();
                double portionsize = Double.parseDouble(portionSize.getText().toString());
                double protein = Double.parseDouble(proteinValue.getText().toString());
                double carb = Double.parseDouble(carbValue.getText().toString());
                double fats = Double.parseDouble(fatsValue.getText().toString());
                double fiber = Double.parseDouble(fiberValue.getText().toString());
                double alcohol = Double.parseDouble(alcoholValue.getText().toString());
                double sugar = Double.parseDouble(sugarValue.getText().toString());
                double kcal = alcohol * 7 + carb * 4 + protein * 4 + fats * 9;
                userfooddiary.addMeals(date, name, kcal, portionsize, protein, carb, fats, alcohol, fiber, sugar);}
                catch(Exception e){ // error if not all boxes answered
                    ErrorPopUp error = new ErrorPopUp("ERROR", "Fill all the boxes, insert 0 even if there's no value.");
                    error.show(getSupportFragmentManager(), "ERROR");
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public boolean testPortionSize(){ // testing the portionsize to be numeral
        try{
            double d = Double.parseDouble(portionSizeComesHere.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            ErrorPopUp error = new ErrorPopUp("ERROR", "Portion size must be in numeral form");
            error.show(getSupportFragmentManager(), "ERROR");
            return false;
        }}

}

