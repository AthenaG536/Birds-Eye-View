package com.example.birdseyeview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class Birding extends AppCompatActivity {
    Button btnContinue, btnSearch, btnRefresh;
    TextView txtVwDetails, txtVwLatLong;
    EditText edtTxtSpecies;
    ConstraintLayout conLayoutTop, conLayoutBottom, conLayout;
    JSONArray selectedBird;
    bird birdDetails = new bird();
    String speciesCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds);
        //setContentView(R.layout.activity_birds);
        conLayoutTop =findViewById(R.id.consLayoutTop);
        conLayoutBottom = findViewById(R.id.conLayoutBottom);
        conLayout = findViewById(R.id.constraintLayout);


        txtVwDetails = findViewById(R.id.txtVwDetails);
        txtVwLatLong = findViewById(R.id.txtVwLatLong);
        edtTxtSpecies = findViewById(R.id.edtTxtSpecies);

        btnContinue = findViewById(R.id.btnContinue);
        btnSearch = findViewById(R.id.btnSearch);
        btnRefresh = findViewById(R.id.btnRefresh);
        try{
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Birding.this, MapsActivity.class));

                }
            });
            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Birding.this.getLastLocation();

                }
            });

            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Birding.this.volleyRequest();

                }
            });
        }catch(NullPointerException e){
            Log.println(Log.ERROR,"btnListeners","Null Pointer");
        }


    }

    public String getSpeciesCode() {
        return speciesCode;
    }

    public void setSpeciesCode(String speciesCode) {
        this.speciesCode = speciesCode;
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    location.getLatitude();
                    String tempLongLat = "Longitude: ";
                    tempLongLat += (location.getLongitude());
                    tempLongLat += "Latitude:";
                    tempLongLat += ((location.getLatitude()));
                    txtVwLatLong.setText(tempLongLat);

                    System.err.println(tempLongLat);
                }
            }
        });
        locationClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MapDemoActivity", "Error trying to get last GPS location");
                e.printStackTrace();
                txtVwLatLong.setText(e.getMessage().toString());
            }
        });
    }

    public ArrayList<String> getTextFromWeb(String urlString)
    {
        URLConnection feedUrl;
        ArrayList<String> allSpeciesInfoArray = new ArrayList<>();

        try
        {
            feedUrl = new URL(urlString).openConnection();
            InputStream is = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                allSpeciesInfoArray.add(line);
            }
            is.close();

            return allSpeciesInfoArray;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void findSpeciesCode(){
        String speciesName = String.valueOf(edtTxtSpecies.getText());
        String speciesNameCorrectCase = "";

        if(!speciesName.isEmpty()){
            StringBuilder builder = new StringBuilder(speciesName);
            boolean space = true;
            for (int i = 0; i < builder.length(); ++i) {
                char c = builder.charAt(i);
                if (space) {
                    if (!Character.isWhitespace(c)) {
                        // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(c));
                        space = false;
                    }
                } else if (Character.isWhitespace(c)) {
                    space = true;
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c));
                }
            }
            speciesNameCorrectCase = builder.toString();
        }



        String allBirdsURL = "ebird.txt";
        String[] attributes;
        String[] speciesInfoWithCode = new String[0];
        try {
            //InputStream birdsFile = this.getAssets()("ebird.txt");
            //File birdFile = new File(allBirdsURL);
            //birdFile.canRead();
            InputStream inputStream = this.getResources().openRawResource(R.raw.ebird);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //BufferedReader reader = new BufferedReader(new InputStreamReader(In));

            String line = reader.readLine();
            while(line != null){
                if(line.length() > 0) {
                    attributes = line.split(",");
                    for (String attr : attributes) {
                        if (attr.equals(speciesNameCorrectCase)) {
                            speciesInfoWithCode = attributes;
                        }
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            inputStream.close();

            if(speciesInfoWithCode.length <1){
                Birding.this.setSpeciesCode("tui1");
            }
            else {
                Birding.this.setSpeciesCode(speciesInfoWithCode[2]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void volleyRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(Birding.this.getApplicationContext());
        String url = "https://api.ebird.org/v2/ref/taxonomy/ebird?fmt=json&species=";
        findSpeciesCode();
        String specCode = this.getSpeciesCode();
        if(specCode == null){
            String errorInfo = "Sorry, no match for " + String.valueOf(edtTxtSpecies.getText()) + ". \nPlease make sure you spelt the species correctly or be more specific.";
            txtVwDetails.setText(errorInfo);
        }
        else {
            url += specCode;

            Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    String birdInfo = "";

                    if (response.toString().isEmpty()) {
                        birdInfo += "Error: no bird selected";
                        txtVwDetails.setText(birdInfo);
                    } else {
                        Birding.this.setSelectedBird(response);
                    /*
                    for(int i = 0; i<Birding.this.getSelectedBird().length(); i++){
                        try {
                            birdInfo += "\n" + Birding.this.getSelectedBird().get(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Used the below for reference
                        //Log.v(TAG, "key = " + jobject.names().getString(i) + " value = " + jobject.get(jobject.names().getString(i)));
                    }*/
                        String checkIsEmpty = Birding.this.getBirdDetails().toString();
                        if (checkIsEmpty == null) {
                            String uhoh = "Uh oh...";
                            txtVwDetails.setText(uhoh);

                        } else {

                            birdInfo += checkIsEmpty;
                            txtVwDetails.setText(birdInfo);
                        }
                    }

                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String anError = error.getMessage().toString();
                    txtVwDetails.setText(anError);
                    Log.d("errorVOL", error.getMessage());
                }
            };
            JsonArrayRequest arrayRequest;
            arrayRequest = new JsonArrayRequest(Method.GET, url, (String) null, responseListener, errorListener);
            requestQueue.add(arrayRequest);
        }
    }

    public bird getBirdDetails() {
        return birdDetails;
    }

    public void setBirdDetails(bird birdDetails) {
        this.birdDetails = birdDetails;
    }

    public JSONArray getSelectedBird() {
        return selectedBird;
    }

    public void setSelectedBird(JSONArray selectedBird) {
        this.selectedBird = selectedBird;
        bird tempBird = new bird();
        try {
            /*
            ArrayList<String> tempDetailArray = new ArrayList<String>();
            for(int i = 0; i < selectedBird.length(); i++){
                if(selectedBird.get(i) == null){
                    tempDetailArray.add(null)
                }else{
                    tempDetailArray.add(selectedBird["SCIENTIFIC_NAME"]);
                }
            }*/
            if(selectedBird.getJSONObject(0) == null){
                Log.d("Error","There is no bird to select");
            }
            else{

            Log.d("Error",selectedBird.getJSONObject(0).toString());
                try {
                    if(selectedBird.getJSONObject(0).getString("sciName") != null){
                        tempBird.setScientificName((String) selectedBird.getJSONObject(0).getString("sciName").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("comName") != null){
                        tempBird.setCommonName((String) selectedBird.getJSONObject(0).getString("comName").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("speciesCode") != null){
                        tempBird.setSpeciesCode((String) selectedBird.getJSONObject(0).getString("speciesCode").toString());
                        Log.d("Tried",selectedBird.getJSONObject(0).getString("speciesCode"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("category") != null) {
                        tempBird.setCategory((String) selectedBird.getJSONObject(0).getString("category").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("taxonOrder") != null){
                        tempBird.setSpeciesCode((String) selectedBird.getJSONObject(0).getString("taxonOrder"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("comNameCodes") != null){
                        tempBird.setCommonNameCodes((String) selectedBird.getJSONObject(0).getString("comNameCodes"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("sciNameCodes") != null)
                    {
                        tempBird.setScientificNameCodes((String) selectedBird.getJSONObject(0).getString("sciNameCodes"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(selectedBird.getJSONObject(0).getString("bandingCodes") != null){
                        tempBird.setBandingCodes((String) selectedBird.getJSONObject(0).getString("bandingCodes").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(selectedBird.getJSONObject(0).getString("order") != null) {
                        tempBird.setOrder((String) selectedBird.getJSONObject(0).getString("order").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("familyComName") != null){
                        tempBird.setFamilyCommonName((String) selectedBird.getJSONObject(0).getString("familyComName").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("familySciName") != null){
                    tempBird.setFamilyScientificName((String) selectedBird.getJSONObject(0).getString("familySciName").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(selectedBird.getJSONObject(0).getString("reportAs") != null){
                        tempBird.setReportAs((String) selectedBird.getJSONObject(0).getString("reportAs").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("extinct") != null) {
                        tempBird.setExtinct((String) selectedBird.getJSONObject(0).getString("extinct").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(selectedBird.getJSONObject(0).getString("extinctYear") != null) {
                        tempBird.setExtinctYear((String) selectedBird.getJSONObject(0).getString("extinctYear").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }





        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error","the Bird has left the nest");
        }

        this.setBirdDetails(tempBird);
    }

}
