package com.hotsoup;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class foodDataHarvester extends AppCompatActivity implements Serializable {
    EditText foodNameComesHere, dateComesHere, portionSizeComesHere;
    String food;
    Button showAddedMeals;
    ListView foodview, mealsEaten;
    userFoodDiary userfooddiary = userFoodDiary.getInstance();
    ArrayList<String> foodinfo = new ArrayList<>();
    ArrayList<String> usersMealInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealdata);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        portionSizeComesHere = (EditText) findViewById(R.id.portionSizeHere);
        foodNameComesHere = (EditText) findViewById(R.id.foodTextComesHere);
        dateComesHere = (EditText) findViewById(R.id.dateComesHere);
        foodview = (ListView) findViewById(R.id.foodsParsedFromApi);
        mealsEaten = (ListView) findViewById(R.id.mealsAdded);
        showAddedMeals = (Button)findViewById(R.id.showaddedMeals);

        showAddedMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatesMeals();
            }
        });


        foodview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                food = parent.getItemAtPosition(position).toString();
                readJSON(food, false);
            }
        });


        foodNameComesHere.addTextChangedListener(new TextWatcher() {
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


    }

    public void addDatesMeals(){
        objectToString();
        ArrayAdapter<String> userFoodsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usersMealInfo);
        mealsEaten.setAdapter(userFoodsAdapter);
        mealsEaten.deferNotifyDataSetChanged();
    }

    public void readJSON(String food, Boolean choice) { //boolean checks if the method call comes from textchange or user clicking element on screen
        String json = getJSON(food);
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                String info;
                if (choice == true) {
                    foodinfo.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        info = jsonObject.getJSONObject("name").getString("fi");
                        foodinfo.add(info);
                    }
                    ArrayAdapter foodadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodinfo);
                    foodview.setAdapter(foodadapter);
                    foodview.deferNotifyDataSetChanged();
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if ((jsonObject.getJSONObject("name").getString("fi")).equals(food)) {
                            String name = food;
                            String date = getDateText();
                            double portionsize = getPortionSize();
                            double fats = Double.parseDouble(jsonObject.getString("fat")) * portionsize/100;
                            double protein = Double.parseDouble(jsonObject.getString("protein")) * portionsize/100;
                            double carb = Double.parseDouble(jsonObject.getString("carbohydrate")) * portionsize/100;
                            double kcal = Double.parseDouble(jsonObject.getString("energyKcal")) * portionsize/100;
                            double alcohol = Double.parseDouble(jsonObject.getString("alcohol")) * portionsize/100;
                            double fiber = Double.parseDouble(jsonObject.getString("fiber")) * portionsize/100;
                            double sugar = Double.parseDouble(jsonObject.getString("sugar")) * portionsize/100;
                            userfooddiary.addMeals(date, name, kcal, portionsize, protein, carb, fats, alcohol, fiber, sugar);
                            System.out.println("DONE");
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDateText(){
        if(dateComesHere.getText().toString() == null || dateComesHere.getText().toString().isEmpty()){
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date dateobject = new Date();
            String datestring = formatter.format(dateobject).toString();
            return datestring;
        }else{
            String datestring = dateComesHere.getText().toString();
            return datestring;
        }
    }

    public Double getPortionSize(){
        double pts;
        if(portionSizeComesHere.getText().toString().isEmpty()){
            pts = 100.0;
            return pts;
        }else{
            pts = Double.parseDouble(portionSizeComesHere.getText().toString());
        }
        return pts;
    }


    public String getJSON(String food) {
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




    public void objectToString(){
        DecimalFormat df = new DecimalFormat("#.#");
        usersMealInfo.clear();
        String date = getDateText();
        ArrayList<userMeal> umeals = userfooddiary.getArray(date);
        for(int i = 0; i<umeals.size(); i++){
            userMeal um = umeals.get(i);
            String meal = um.getFoodname()+"" +
                    "\nPortion: "+um.portionsize+"g"+
                    "\nCalories: "+df.format(um.getEnergy())+"kcal"+
                    "\nfats: "+df.format(um.getFats())+"g" +
                    "\nProtein: "+df.format(um.getProtein())+"g" +
                    "\nCarbohydrates: "+df.format(um.getCarb())+"g" +
                    "\n     Dietary fiber: "+df.format(um.getFiber())+"g" +
                    "\n     Sugar: "+df.format(um.getSugar())+"g";
            usersMealInfo.add(meal);
        }
    }
}
