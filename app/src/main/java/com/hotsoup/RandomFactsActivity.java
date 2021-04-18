package com.hotsoup;

import android.content.Intent;
import android.graphics.Color;
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
    Button showme, backbutton;
    TextView title, extrainfo, sourceView;

    final String extradatafromTHL = "Joka toinen tupakoija kuolee ennenaikaisesti tupakan aiheuttamiin sairauksiin, jos jatkaa tupakointiaan.\n\nSuomessa tupakoinnista aiheutuvia ennenaikaisia kuolemia on vuosittain noin 4000.\n\n" +
            "Tupakoinnin terveyshaitat ovat moninaiset vaikuttaen koko kehoon. Haitat ovat laajasti raportoidut mutta uusia haittavaikutuksia löydetään edelleen.\n\nMerkittävimmät tupakoinnin aiheuttamat sairaudet ovat syöpä-, hengityselin- ja verenkiertoelimistön sairaudet.\n\n" +
            "Lähde:\n https://thl.fi/fi/web/alkoholi-tupakka-ja-riippuvuudet/tupakka/tupakkatuotteet-ja-sahkosavuke/savuke";
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
        backbutton = (Button) findViewById(R.id.randomFactsBackButton);
        callTheMaleReadJson();
        callTheFemaleReadJson();
        title = (TextView) findViewById(R.id.titleforBarchart);
        extrainfo = (TextView) findViewById(R.id.moreInfoTobacco);

        showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValueToFragment();
                System.out.println("done");
                title.setTextSize(16);
                title.setTextColor(Color.BLACK);
                title.setText("Kaaviossa on esitetty päivittäin tupakoivien 20 vuotta täyttäneiden osuus prosentteina vastaavanikäisestä suomalaisesta väestöstä.");
                extrainfo.setTextSize(16);
                extrainfo.setTextColor(Color.BLACK);
                extrainfo.setText(extradatafromTHL);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtomainmenu();
            }
        });
    }

    public void backtomainmenu(){
        Intent myIntent = new Intent(this, MainScreenActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void callTheMaleReadJson() {
        readJSON(2017, "Male");
        readJSON(2018, "Male");
        readJSON(2019, "Male");

    }
    private void callTheFemaleReadJson(){
        readJSON(2017, "Female");
        readJSON(2018, "Female");
        readJSON(2019, "Female");
    }


    public void readJSON(int year, String gender){
        String json = getJSON(year, gender);

        if(json != null){
            if (gender.equals("Male")){
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

    public String getJSON(int yr, String gd){
        String response = null;
        try{
            URL url = new URL("https://sotkanet.fi/rest/1.1/json?indicator=4404&regions=658&years="+yr+"&genders="+gd);
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
