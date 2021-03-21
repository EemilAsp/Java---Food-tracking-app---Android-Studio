package com.example.datahaku;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

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

import javax.net.ssl.HttpsURLConnection;

public class foodDataHarvester {
        private static foodDataHarvester fd = new foodDataHarvester();
        public String food;
        private foodDataHarvester(){}

public static foodDataHarvester getInstance(){return fd;}



public void readGSON(String s){
            food = s;
    String json = getJSON(food);
    System.out.println("JSON:"+ json);

    if (json != null){
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("############"+ (i+1) +"##########");
                System.out.println(jsonObject.getString("name"));
                System.out.println("Energia Kcal:"+jsonObject.getString("energyKcal"));
                System.out.println("Rasvaa:"+jsonObject.getString("fat"));
                System.out.println("Proteiinia"+jsonObject.getString("protein"));
                System.out.println("Hiilaria"+jsonObject.getString("carbohydrate"));
                System.out.println("Alkoholia:"+jsonObject.getString("alcohol"));
                System.out.println("Kuitua"+jsonObject.getString("fiber"));
                System.out.println("Sokeria"+jsonObject.getString("sugar"));
                //System.out.println(jsonObject.getDouble("mass"));
                //if(jsonObject.getDouble("mass") == 100.0){
                    //System.out.println(jsonObject.getString("energyKcal"));}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


public String getJSON(String food){
    String response = null;
    try {
        URL url = new URL("https://fineli.fi/fineli/api/v1/foods?q="+food); //food on nyt
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while((line = br.readLine())!= null){
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
}
