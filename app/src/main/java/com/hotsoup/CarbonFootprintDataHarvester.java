package com.hotsoup;

import android.os.StrictMode;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CarbonFootprintDataHarvester {

    public void CarbonFootprintDataHarvester(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static double readfoodJSON(URL url){  //gets the wanted URL as parameter and uses API to get the correct food carbon footprint
        String json=getJSON(url);
        double carbonfootprint=0.00;

        if(json != null){
            try{
                JSONObject object = new JSONObject(json);
                carbonfootprint = Math.round((object.getDouble("Total")) * 100.0) / 100.0;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return carbonfootprint;
    }

    public static double readGasJSON(URL url){        //gets the wanted URL as parameter and uses API to get the correct car carbon footprint
        double gascarbonfootprint=0.00;
        if(url==null){
            return 0.00;
        }
        else{
            String json=getJSON(url);
            if(json!=null){
                try{
                    JSONObject object = new JSONObject(json);
                    gascarbonfootprint=Math.round((object.getDouble("carbonFootprint"))*100.0/100.0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    return gascarbonfootprint;
    }

    public static String getJSON(URL url){          //gets the url as parameter and constructs a string from the JSON API
        String response=null;
        try{
            HttpURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb= new StringBuilder();
            String line=null;
            while((line = br.readLine())!=null){
                sb.append(line).append("\n");
            }
            response=sb.toString();
            in.close();


    } catch (ProtocolException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
        return response;
    }}
