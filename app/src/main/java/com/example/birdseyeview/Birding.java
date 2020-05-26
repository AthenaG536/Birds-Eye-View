package com.example.birdseyeview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Birding extends AppCompatActivity {
    Button btnContinue, btnSearch, btnRefresh;
    TextView txtVwDetails, txtVwLatLong;
    EditText edtTxtSpecies;
    ConstraintLayout conLayoutTop, conLayoutBottom, conLayout;
    JSONArray selectedBird;
    bird birdDetails = new bird();
    private Object ;


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

    public void volleyRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(Birding.this.getApplicationContext());
        String url = "https://api.ebird.org/v2/ref/taxonomy/ebird?fmt=json&species=tui1";

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                String birdInfo = "";

                if(response.toString().isEmpty()){
                    birdInfo += "Error: no bird selected";
                    txtVwDetails.setText(birdInfo);
                }
                else{
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
                    birdInfo += Birding.this.getBirdDetails().toString();

                    if(birdInfo.isEmpty()){
                        String uhoh = "Uh oh...";
                        txtVwDetails.setText(uhoh);
                    }else{
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
                Log.d("errorVOL",error.getMessage());
            }
        };
        JsonArrayRequest arrayRequest;
        arrayRequest = new JsonArrayRequest(Method.GET, url, (String) null, responseListener,errorListener);
        requestQueue.add(arrayRequest);
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
            ArrayList<String> tempDetailArray = new ArrayList<String>();
            for(int i = 0; i < selectedBird.length(); i++){
                if(selectedBird.get(i) == null){
                    tempDetailArray.add(null)
                }else{
                    tempDetailArray.add(selectedBird["SCIENTIFIC_NAME"]);
                }
            }

            tempBird.setScientificName((String) selectedBird.get("SCIENTIFIC_NAME"));
            tempBird.setCommonName((String) selectedBird.get("COMMON_NAME"));
            tempBird.setSpeciesCode((String) selectedBird.get("SPECIES_CODE"));
            tempBird.setCategory((String) selectedBird.get("CATEGORY"));
            tempBird.setSpeciesCode((String) selectedBird.get("TAXON_ORDER"));
            tempBird.setCommonNameCodes((String) selectedBird.get("COM_NAME_CODES"));
            tempBird.setScientificNameCodes((String) selectedBird.get("SCI_NAME_CODES"));

            tempBird.setBandingCodes((String) selectedBird.get("BANDING_CODES"));
            tempBird.setOrder((String) selectedBird.get("ORDER"));
            tempBird.setFamilyCommonName((String) selectedBird.get("FAMILY_COM_NAME"));
            tempBird.setFamilyScientificName((String) selectedBird.get("FAMILY_SCI_NAME"));
            tempBird.setReportAs((String) selectedBird.get("REPORT_AS"));
            tempBird.setExtinct((String) selectedBird.get("EXTINCT"));
            tempBird.setExtinctYear((String) selectedBird.get("EXTINCT_YEAR"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.setBirdDetails(tempBird);
    }

}
