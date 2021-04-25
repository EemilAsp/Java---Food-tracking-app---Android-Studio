package com.hotsoup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
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
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    TextView ricetext;
    TextView saladtext;
    TextView beeftext;
    TextView porkchickentext;
    TextView fishtext;
    TextView dairytext;
    TextView cheesetext;
    TextView eggtext;
    TextView kmdriventext;
    TextView lowCBtext;

    SeekBar riceseekbar;
    SeekBar saladseekbar;
    SeekBar beefseekbar;
    SeekBar porkchickenseekbar;
    SeekBar fishseekbar;
    SeekBar dairyseekbar;
    SeekBar cheeseseekbar;
    SeekBar eggseekbar;
    SeekBar kmdrivenseekbar;

    FrameLayout piechart;

    Button continuecount;
    Button countCB;
    Spinner dietspinner;
    Spinner cartypespinner;
    Switch lowCBpref;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        continuecount = findViewById(R.id.continuecount);
        countCB=findViewById(R.id.countCB);
        lowCBpref=findViewById(R.id.lowCBpref);

        piechart = findViewById(R.id.piechartView);
        piechart.setVisibility(View.GONE);

        ricetext = findViewById(R.id.riceText);
        saladtext = findViewById(R.id.saladText);
        beeftext = findViewById(R.id.beefText);
        porkchickentext = findViewById(R.id.porkchickenText);
        eggtext = findViewById(R.id.eggText);
        fishtext = findViewById(R.id.fishText);
        dairytext = findViewById(R.id.dairyText);
        cheesetext = findViewById(R.id.cheeseText);
        kmdriventext=findViewById(R.id.kmdrivenText);
        lowCBtext=findViewById(R.id.lowCBtext);

        riceseekbar = findViewById(R.id.riceSeekbar);
        saladseekbar = findViewById(R.id.saladSeekbar);
        beefseekbar = findViewById(R.id.beefSeekbar);
        porkchickenseekbar = findViewById(R.id.porkchickenSeekbar);
        eggseekbar = findViewById(R.id.eggSeekbar);
        fishseekbar = findViewById(R.id.fishSeekbar);
        dairyseekbar = findViewById(R.id.dairySeekbar);
        cheeseseekbar = findViewById(R.id.cheeseSeekbar);
        kmdrivenseekbar=findViewById(R.id.kmdrivenseekbar);


        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });

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

        kmdrivenseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                kmdriventext.setText("Ajokilometrit/vuosi: " + progress + "km");
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
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.diets));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietspinner.setAdapter(adapter1);
        dietspinner.setOnItemSelectedListener(this);

        cartypespinner=findViewById(R.id.cartypespinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.cartypes));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cartypespinner.setAdapter(adapter2);
        cartypespinner.setOnItemSelectedListener(this);
    }


    public void readfoodJSON(View v){           //finds the carbon footprint of food using harvester method and makes the interface go to the "next screen" a.k.a makes car related information visible
        url=getfoodURL();
        double carbonfootprint;
        carbonfootprint = CarbonFootprintDataHarvester.readfoodJSON(url);
        cartypespinner.setVisibility(View.VISIBLE);
        kmdrivenseekbar.setVisibility(View.VISIBLE);
        kmdriventext.setVisibility(View.VISIBLE);
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
        lowCBpref.setVisibility(View.GONE);
        lowCBtext.setVisibility(View.GONE);
        dietspinner.setVisibility(View.GONE);
        continuecount.setVisibility(View.GONE);
        countCB.setVisibility(View.GONE);
        System.out.println(carbonfootprint);
        user.carbonfootprint.add(carbonfootprint); //Adds the carbons footprint value to array
    }

    public URL getfoodURL() {       //constructs the food URL based on selected values
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

    public void readGasJSON(View v){               //finds carbon footprint of car usage using harvester method
        double gascarbonfootprint;
        url=getGasURL();
        gascarbonfootprint=CarbonFootprintDataHarvester.readGasJSON(url);
        user.travelcarbonfootprint.add(gascarbonfootprint*0.1);
        senddatatoChart();
    }

    public URL getGasURL(){                     //constructs the car URL based on selected values
        String cartype;
        if(cartypespinner.getSelectedItemPosition()==2){
            cartype="petrolCar";
        }
        else if(cartypespinner.getSelectedItemPosition()==3){
            cartype="dieselCar";
        }
        else{
            return null;    //return null if user has selected no car or hasn't chosen any alternative
        }
        try {
            url = new URL("https://api.triptocarbon.xyz/v1/footprint?activity="+ kmdrivenseekbar.getProgress()/1.61 +"&activityType=miles&country=gbr&mode="+cartype);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.dietspinner){  //chooses which elements of diet are visible based on selected diet

            if(position==0){
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
                lowCBpref.setVisibility(View.GONE);
                lowCBtext.setVisibility(View.GONE);
            }
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
        continuecount.setVisibility(View.VISIBLE);
        lowCBpref.setVisibility(View.VISIBLE);
        lowCBtext.setVisibility(View.VISIBLE);
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
        continuecount.setVisibility(View.VISIBLE);
        lowCBpref.setVisibility(View.VISIBLE);
        lowCBtext.setVisibility(View.VISIBLE);
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
        continuecount.setVisibility(View.VISIBLE);
        lowCBpref.setVisibility(View.VISIBLE);
        lowCBtext.setVisibility(View.VISIBLE);
    }
    }
        if(parent.getId()==R.id.cartypespinner){
            if(position==0){
                kmdriventext.setVisibility(View.GONE);
                kmdrivenseekbar.setVisibility(View.GONE);
                countCB.setVisibility(View.GONE);
            }
            if(position==1){
                kmdriventext.setVisibility(View.GONE);
                kmdrivenseekbar.setVisibility(View.GONE);
                countCB.setVisibility(View.VISIBLE);
            }
            else{
                kmdriventext.setVisibility(View.VISIBLE);
                kmdrivenseekbar.setVisibility(View.VISIBLE);
                countCB.setVisibility(View.VISIBLE);
            }
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
        lowCBpref.setVisibility(View.GONE);
        lowCBtext.setVisibility(View.GONE);
        kmdriventext.setVisibility(View.GONE);
        kmdrivenseekbar.setVisibility(View.GONE);
    }

    private void senddatatoChart() {
        Fragment frag = new fragment_carbonFootPrintPiechart();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.piechartView, frag);
        transaction.commit();
        piechart.setVisibility(View.VISIBLE);
    }

    private void goMainScreen(){
        Intent myIntent = new Intent(this, MainScreenActivity.class);
        startActivity(myIntent);
        finish();}
}