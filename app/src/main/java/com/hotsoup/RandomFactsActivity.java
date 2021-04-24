package com.hotsoup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;



public class RandomFactsActivity extends AppCompatActivity { // this activity takes data from Sotkanet API and shows to user as a barchart.
    Fragment fragment = new tobacco_barchart();
    Button showme;
    TextView title, extrainfo;
    OutputStreamWriter ows = null;
    FileInputStream fis = null;
    final String FILENAME = "Tobaccoapidata.txt";
    final String extradatafromTHL = "Joka toinen tupakoija kuolee ennenaikaisesti tupakan aiheuttamiin sairauksiin, jos jatkaa tupakointiaan.\n\nSuomessa tupakoinnista aiheutuvia ennenaikaisia kuolemia on vuosittain noin 4000.\n\n" +
            "Tupakoinnin terveyshaitat ovat moninaiset vaikuttaen koko kehoon. Haitat ovat laajasti raportoidut mutta uusia haittavaikutuksia löydetään edelleen.\n\nMerkittävimmät tupakoinnin aiheuttamat sairaudet ovat syöpä-, hengityselin- ja verenkiertoelimistön sairaudet.\n\n" +
            "Lähde:\n https://thl.fi/fi/web/alkoholi-tupakka-ja-riippuvuudet/tupakka/tupakkatuotteet-ja-sahkosavuke/savuke";
    ArrayList<String> Malelist = new ArrayList<>();
    ArrayList<String> Femalelist = new ArrayList<>();
    Context context = this;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_facts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        showme = (Button) findViewById(R.id.showmebutton);

        callTheMaleReadJson(); // Searches data from sotkanet api
        callTheFemaleReadJson(); // Searches data from sotkanet api
        if(Malelist.size() > 0 && Femalelist.size() > 0){
            savedatatofile(); // incase of api wont work saving the data to backup file
        }
        title = (TextView) findViewById(R.id.titleforBarchart);
        extrainfo = (TextView) findViewById(R.id.moreInfoTobacco);

        showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValueToFragment(); // barchart fragment
                System.out.println("done");
                title.setTextSize(16);
                title.setTextColor(Color.BLACK);
                title.setText("Kaaviossa on esitetty päivittäin tupakoivien 20 vuotta täyttäneiden osuus prosentteina vastaavanikäisestä suomalaisesta väestöstä.");
                extrainfo.setTextSize(16);
                extrainfo.setTextColor(Color.BLACK);
                extrainfo.setText(extradatafromTHL);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
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

    private void savedatatofile() { // saving data to file incase of api wont work / or network doesn't work
        try {
            ows = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            for(int i = 0; i < 3; i++){
                ows.write(Malelist.get(i)+"\n");
            }
            for(int i = 0; i < 3; i++){
                ows.write(Femalelist.get(i)+"\n");
            }
            ows.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void callTheMaleReadJson() { // data for males
        readJSON(2017, "Male");
        readJSON(2018, "Male");
        readJSON(2019, "Male");

    }
    private void callTheFemaleReadJson(){ // data for females
        readJSON(2017, "Female");
        readJSON(2018, "Female");
        readJSON(2019, "Female");
    }


    public void readJSON(int year, String gender){ //JSON reader takes year and gender as input
        String json = getJSON(year, gender);

        if(json != null){
            if (gender.equals("Male")){ //testing the input
            try{
                System.out.println("Läpi1");
                JSONArray jsonArray = new JSONArray(json);
                String info;
                for(int i = 0; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("region").equals("658")){
                info = jsonObject.getString("value");
                System.out.println(info);
                Malelist.add(info);}


            }} catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(gender.equals("Female")){
            try{
                System.out.println("Läpi2");
                JSONArray jsonArray = new JSONArray(json);
                String info;
                for(int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("region").equals("658")){
                        info = jsonObject.getString("value");
                        System.out.println(info);
                        Femalelist.add(info);}
                }} catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }}

    public String getJSON(int yr, String gd){ //Gets the JSON from api
        String response = null;
        try{
            URL url = new URL("https://sotkanet.fi/rest/1.1/json?in5dicator=4404&regions=658&years="+yr+"&genders="+gd);
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

    public void sendValueToFragment(){ // sending data to barchart fragment
        Bundle bundle = new Bundle();
        if(Malelist.isEmpty() && Femalelist.isEmpty()){
            try {
                fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bis = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                int i = 0;
                while((text = bis.readLine()) != null){ // if the api didnt work, getting backupdata from previous search in our opinion this does
                    i++;// check the requirement of using data with not directly being connected to the API
                    if( i < 4){
                        Malelist.add(text);
                        System.out.println(text);

                    }
                    else{
                        Femalelist.add(text);
                        System.out.println(text);
                        i++;
                    }
                }
                fis.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Malelist.toString());
        System.out.println(Femalelist.toString());
        bundle.putStringArrayList("male", Malelist);
        bundle.putStringArrayList("female", Femalelist);

        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentview,fragment);
        transaction.commit();

    }

}
