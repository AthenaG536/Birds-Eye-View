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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import org.json.JSONObject;

public class Birding extends AppCompatActivity {
    Button btnContinue, btnSearch, btnRefresh;
    TextView txtVwDetails, txtVwLatLong;
    EditText edtTxtSpecies;
    ConstraintLayout conLayoutTop, conLayoutBottom, conLayout;


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
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        String url = "https://api.ebird.org/v2/ref/taxonomy/ebird?fmt=json&species=tui1";

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                txtVwDetails.setText(response.toString());
                Log.d("response",response.toString());

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtVwDetails.setText(error.getMessage());
                Log.d("errorVOL",error.getMessage());
            }
        };
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, responseListener, errorListener);
        requestQueue.add(request);
    }

}
