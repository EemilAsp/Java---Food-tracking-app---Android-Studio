package com.hotsoup;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class RandomFactsActivity extends AppCompatActivity {
    private String usershometown;
    Fragment fragment = new tobacco_barchart();
    Button showme;

    final String extradatafromTHL = "Joka toinen tupakoija kuolee ennenaikaisesti tupakan aiheuttamiin sairauksiin, jos jatkaa tupakointiaan.\n Suomessa tupakoinnista aiheutuvia ennenaikaisia kuolemia on vuosittain noin 4000.\n Lähde: www.thl.fi";
    ArrayList<String> Malelist = new ArrayList<>();
    ArrayList<String> Femalelist = new ArrayList<>();
    //kerää data arrayihin male 2017, 2018, 2019
    //Sama juttu female 2017, 2018, 2019
    //intenttinä data sitten barchart fragmenttiin jossa voi luoda sitten diagrammin
    //

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_facts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        showme = (Button) findViewById(R.id.showmebutton);
        callTheReadJson();

        Malelist.add(0, "10.2");
        Malelist.add(1, "12.2");
        Malelist.add(2, "14.2");

        Femalelist.add(0, "10.5");
        Femalelist.add(1, "11.5");
        Femalelist.add(2, "17.5");

        showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValueToFragment();
                System.out.println("done");
            }
        });
    }

    private void callTheReadJson() {
        for(int i = 2017; i < 2019; i++){
            readJSON(i, "Male");
        }
        for(int i = 2017; i < 2019; i++){
            readJSON(i, "Female");
        }
    }


    public void readJSON(int year, String gender){
        String json = getJSON(year, gender);

        if(json != null){
            if (gender.equals("Male")){
            try{
                JSONArray jsonArray = new JSONArray(json);
                String info;
                for(int i = 0; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                info = jsonObject.getJSONObject("indicator").getString("region");
                System.out.println(info);
                //Malelist.add(info);
            }} catch (JSONException e) {
                e.printStackTrace();
            }
        }}else{
            try{
                JSONArray jsonArray = new JSONArray(json);
                String info;
                for(int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    info = jsonObject.getJSONObject("indicator").getString("region");
                    System.out.println(info);
                    //Femalelist.add(info);
                }} catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getJSON(int yr, String gd){
        String response = null;
        try{
            URL url = new URL("https://sotkanet.fi/rest/1.1/json?indicator=4404&years="+yr+"&genders="+gd);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
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

    public void sendValueToFragment(){
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("male", Malelist);
        bundle.putStringArrayList("female", Femalelist);

        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentview,fragment);
        transaction.commit();
    }

}
