package com.hotsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CarbonFootprintActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView ricetext;
    TextView saladtext;
    TextView beeftext;
    TextView porkchickentext;
    TextView fishtext;
    TextView dairytext;
    TextView cheesetext;
    TextView eggtext;

    SeekBar riceseekbar;
    SeekBar saladseekbar;
    SeekBar beefseekbar;
    SeekBar porkchickenseekbar;
    SeekBar fishseekbar;
    SeekBar dairyseekbar;
    SeekBar cheeseseekbar;
    SeekBar eggseekbar;

    Button countCB;
    Spinner dietspinner;
    Switch lowCBpref;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        countCB = findViewById(R.id.countCB);
        lowCBpref=findViewById(R.id.lowCBpref);

        ricetext = findViewById(R.id.riceText);
        saladtext = findViewById(R.id.saladText);
        beeftext = findViewById(R.id.beefText);
        porkchickentext = findViewById(R.id.porkchickenText);
        eggtext = findViewById(R.id.eggText);
        fishtext = findViewById(R.id.fishText);
        dairytext = findViewById(R.id.dairyText);
        cheesetext = findViewById(R.id.cheeseText);

        riceseekbar = findViewById(R.id.riceSeekbar);
        saladseekbar = findViewById(R.id.saladSeekbar);
        beefseekbar = findViewById(R.id.beefSeekbar);
        porkchickenseekbar = findViewById(R.id.porkchickenSeekbar);
        eggseekbar = findViewById(R.id.eggSeekbar);
        fishseekbar = findViewById(R.id.fishSeekbar);
        dairyseekbar = findViewById(R.id.dairySeekbar);
        cheeseseekbar = findViewById(R.id.cheeseSeekbar);

        riceseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ricetext.setText("Riisin kulutus: " + Math.round((progress*0.0018) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saladseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                saladtext.setText("Salaatin kulutus: " + Math.round((progress*0.028) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        beefseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                beeftext.setText("Naudanlihan ja lampaan kulutus: " + Math.round((progress*0.008) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        porkchickenseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                porkchickentext.setText("Sianlihan ja siipikarjan kulutus: " + Math.round((progress*0.02) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        eggseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                eggtext.setText("Kananmunien kulutus: " + progress + " kpl/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fishseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fishtext.setText("Kalan kulutus: " + Math.round((progress*0.012) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dairyseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dairytext.setText("Maitotuotteiden kulutus: " + Math.round((progress*0.076) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cheeseseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cheesetext.setText("Juuston kulutus: " + Math.round((progress*0.006) * 100.0) / 100.0 + " kg/viikko");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        lowCBpref.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            lowCBpref.setText("Kyllä");
        }
        else{
            lowCBpref.setText("En");
        }
    }
});


        dietspinner = findViewById(R.id.dietspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.diets));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietspinner.setAdapter(adapter);
        dietspinner.setOnItemSelectedListener(this);
    }

    public void readJSON(View v){
        url=getJSON();
        double carbonfootprint;
        carbonfootprint = CarbonFootprintDataHarvester.readJSON(url);
        ricetext.setText("Hiilijalanjälki on= "+carbonfootprint+" kg");
    }

    public URL getJSON() {
        String diet = "omnivore";
        String response = null;
        String CP = "false";


        if (lowCBpref.isChecked() == true) {
            CP = "true";
        } else {
            CP = "false";
        }
        try {
            if (dietspinner.getSelectedItemPosition() == 1) {
                diet = "omnivore";
                url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=" + diet + "&query.lowCarbonPreference=" + CP + "&query.beefLevel=" + beefseekbar.getProgress() * 2 + "&query.fishLevel=" + fishseekbar.getProgress() * 2 + "&query.porkPoultryLevel=" + porkchickenseekbar.getProgress() * 2 + "&query.dairyLevel=" + dairyseekbar.getProgress() * 2 + "&query.cheeseLevel=" + cheeseseekbar.getProgress() * 2 + "&query.riceLevel=" + riceseekbar.getProgress() * 2 + "&query.eggLevel=" + eggseekbar.getProgress() / 3 * 100 + "&query.winterSaladLevel=" + saladseekbar.getProgress() * 2 + "&query.restaurantSpending=0");
            } else if (dietspinner.getSelectedItemPosition() == 2) {
                diet = "vegetarian";
                url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=" + diet + "&query.lowCarbonPreference="+ CP +"&query.fishLevel=0&query.porkPoultryLevel=0&query.dairyLevel=" + dairyseekbar.getProgress() * 2 + "&query.cheeseLevel=" + cheeseseekbar.getProgress() * 2 + "&query.riceLevel=" + riceseekbar.getProgress() * 2 + "&query.eggLevel=" + eggseekbar.getProgress() / 3 * 100 + "&query.winterSaladLevel=" + saladseekbar.getProgress() * 2 + "&query.restaurantSpending=0");
            } else {
                diet = "vegan";
                url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=" + diet + "&query.lowCarbonPreference=" + CP + "&query.beefLevel=0&query.fishLevel=0&query.porkPoultryLevel=0&query.dairyLevel=0&query.cheeseLevel=0&query.riceLevel=" + riceseekbar.getProgress() * 2 + "&query.eggLevel=0&query.winterSaladLevel=" + saladseekbar.getProgress() * 2 + "&query.restaurantSpending=0");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if(position==1){
        saladseekbar.setVisibility(View.VISIBLE);
        eggseekbar.setVisibility(View.VISIBLE);
        fishseekbar.setVisibility(View.VISIBLE);
        cheeseseekbar.setVisibility(View.VISIBLE);
        riceseekbar.setVisibility(View.VISIBLE);
        dairyseekbar.setVisibility(View.VISIBLE);
        beefseekbar.setVisibility(View.VISIBLE);
        porkchickenseekbar.setVisibility(View.VISIBLE);
        saladtext.setVisibility(View.VISIBLE);
        eggtext.setVisibility(View.VISIBLE);
        fishtext.setVisibility(View.VISIBLE);
        cheesetext.setVisibility(View.VISIBLE);
        ricetext.setVisibility(View.VISIBLE);
        dairytext.setVisibility(View.VISIBLE);
        beeftext.setVisibility(View.VISIBLE);
        porkchickentext.setVisibility(View.VISIBLE);
        countCB.setVisibility(View.VISIBLE);
    }

    if(position==2){
        saladseekbar.setVisibility(View.VISIBLE);
        eggseekbar.setVisibility(View.VISIBLE);
        fishseekbar.setVisibility(View.GONE);
        cheeseseekbar.setVisibility(View.VISIBLE);
        riceseekbar.setVisibility(View.VISIBLE);
        dairyseekbar.setVisibility(View.VISIBLE);
        beefseekbar.setVisibility(View.GONE);
        porkchickenseekbar.setVisibility(View.GONE);
        saladtext.setVisibility(View.VISIBLE);
        eggtext.setVisibility(View.VISIBLE);
        fishtext.setVisibility(View.GONE);
        cheesetext.setVisibility(View.VISIBLE);
        ricetext.setVisibility(View.VISIBLE);
        dairytext.setVisibility(View.VISIBLE);
        beeftext.setVisibility(View.GONE);
        porkchickentext.setVisibility(View.GONE);
        countCB.setVisibility(View.VISIBLE);
    }

    if(position==3){
        saladseekbar.setVisibility(View.VISIBLE);
        eggseekbar.setVisibility(View.GONE);
        fishseekbar.setVisibility(View.GONE);
        cheeseseekbar.setVisibility(View.GONE);
        riceseekbar.setVisibility(View.VISIBLE);
        dairyseekbar.setVisibility(View.GONE);
        beefseekbar.setVisibility(View.GONE);
        porkchickenseekbar.setVisibility(View.GONE);
        saladtext.setVisibility(View.VISIBLE);
        eggtext.setVisibility(View.GONE);
        fishtext.setVisibility(View.GONE);
        cheesetext.setVisibility(View.GONE);
        ricetext.setVisibility(View.VISIBLE);
        dairytext.setVisibility(View.GONE);
        beeftext.setVisibility(View.GONE);
        porkchickentext.setVisibility(View.GONE);
        countCB.setVisibility(View.VISIBLE);
    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        saladseekbar.setVisibility(View.GONE);
        eggseekbar.setVisibility(View.GONE);
        fishseekbar.setVisibility(View.GONE);
        cheeseseekbar.setVisibility(View.GONE);
        riceseekbar.setVisibility(View.GONE);
        dairyseekbar.setVisibility(View.GONE);
        beefseekbar.setVisibility(View.GONE);
        porkchickenseekbar.setVisibility(View.GONE);
        saladtext.setVisibility(View.GONE);
        eggtext.setVisibility(View.GONE);
        fishtext.setVisibility(View.GONE);
        cheesetext.setVisibility(View.GONE);
        ricetext.setVisibility(View.GONE);
        dairytext.setVisibility(View.GONE);
        beeftext.setVisibility(View.GONE);
        porkchickentext.setVisibility(View.GONE);
    }
}