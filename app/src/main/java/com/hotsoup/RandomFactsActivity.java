package com.hotsoup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView tobaccodata;
    Spinner yearSpinner;
    Spinner genderSpinner;
    String year = "2019";
    String gender = "Total";
    final String extradatafromTHL = "Joka toinen tupakoija kuolee ennenaikaisesti tupakan aiheuttamiin sairauksiin, jos jatkaa tupakointiaan.\n Suomessa tupakoinnista aiheutuvia ennenaikaisia kuolemia on vuosittain noin 4000.\n Lähde: www.thl.fi";
    ArrayList<String> genderlist = new ArrayList<>();
    ArrayList<String> yearlist = new ArrayList<>();
    //Daily smokers id 4404 (december - november)
    //huonosti dataa tietyn paikkakunnan tiedoista.
    //Pitäisikö laittaa vaan koko suomen luvut? parin vuoden ajalta?
    //https://sotkanet.fi/rest/1.1/json?indicator=4404&years=2019&genders=female
    // female / male / total all of these
    // 2019
    // whole finland region coded 658
    //

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_facts);
        tobaccodata = findViewById(R.id.tobaccoDataHere);
        yearSpinner = findViewById(R.id.yearSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
        setupLists();

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setupLists(){ //setup the spinner data
        genderlist.add("Total");
        genderlist.add("Male");
        genderlist.add("Female");
        yearlist.add("2019");
        yearlist.add("2018");
        yearlist.add("2017");
        yearlist.add("2016");
    }


    public void readJSON(String year, String gender){
        String json = getJSON(year, gender);
        if(json != null){
            try{
                JSONArray jsonArray = new JSONArray(json);
                String info;
                for(int i = 0; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                info = jsonObject.getJSONObject("indicator").getString("region");
                System.out.println(info);
            }} catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getJSON(String yr, String gd){
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
}
