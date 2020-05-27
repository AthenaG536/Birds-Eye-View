package com.example.birdseyeview;

import android.location.LocationListener;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 8.0f;

        // Add a marker in Hanua Falls and move the camera
        LatLng location = new LatLng(-37.06877, 175.08977);
        mMap.addMarker(new MarkerOptions().position(location).title("Hanua Falls"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));


        location = new LatLng(-36.80192, 175.10801);
        mMap.addMarker(new MarkerOptions().position(location).title("Waiheke Island"));

        location = new LatLng(-36.43212, 174.83222);
        mMap.addMarker(new MarkerOptions().position(location).title("Kawau Island"));
        location = new LatLng(-36.83273, 174.42431);
        mMap.addMarker(new MarkerOptions().position(location).title("Muriwai Gannet Colony"));
        location = new LatLng(-36.25786, 175.42778);
        mMap.addMarker(new MarkerOptions().position(location).title("Great Barrier Island"));

        location = new LatLng(-36.60124, 174.88936);
        mMap.addMarker(new MarkerOptions().position(location).title("Tiritiri Matangi Island"));
    }

}
