package com.example.bloodonation1.Presesntation;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bloodonation1.Checker.Checker;
import com.example.bloodonation1.Models.RemoteRequestModel;
import com.example.bloodonation1.Presesntation.ViewModel.MapViewModel;
import com.example.bloodonation1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionGranted = false;

    private GoogleMap map;
    private FusedLocationProviderClient mFuesdLocationProviderClient;


    static Button viewMarker;
    private double currentLatitude;
    private double currentLongitude;
    private List<Marker> markerList = new ArrayList<>();

    private MapViewModel mapViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermission();

        viewMarker = findViewById(R.id.view_marker);
        if (Checker.checker){
            viewMarker.setVisibility(View.GONE);
        }

        else{
            viewMarker.setVisibility(View.VISIBLE);
        }

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);




        if (mPermissionGranted){
            getCurrentLocation();
        }

        viewMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void getDeviceLocation(){
        mFuesdLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mPermissionGranted) {
                Task location = mFuesdLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if( task.isSuccessful()){
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),15f);
                    }

                    else{
                        Toast.makeText(MapActivity.this, "The location is not provided", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (SecurityException e){
            e.printStackTrace();

        }

    }

    private void moveCamera(LatLng latLng, float zoom){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void addMarker (String title, String snippet) {
        MarkerOptions options = new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude))
                .title(title)
                .snippet(snippet);

        markerList.add(map.addMarker(options));
    }


    private void getCurrentLocation(){

        mFuesdLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (mPermissionGranted) {
            Task location = mFuesdLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location currentLocation = (Location) task.getResult();
                    currentLatitude = currentLocation.getLatitude();
                    currentLongitude = currentLocation.getLongitude();

                }

                else{
                    Toast.makeText(MapActivity.this, "Error while getting the current location", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setMyLocationEnabled(true);
                Toast.makeText(MapActivity.this, "Your map is ready", Toast.LENGTH_SHORT).show();
                getBloodRequests();
            }
        });
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
            if(grantResults.length > 0){
                for (int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        mPermissionGranted = false;
                        return;
                    }
                }
                mPermissionGranted = true;
                initMap();
            }

        }
    }

    // fetch blood requests from DB
    private void getBloodRequests () {
        mapViewModel.getBloodRequests().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (RemoteRequestModel model: task.getResult().toObjects(RemoteRequestModel.class)) addMarker(model.getBloodType(),"Name: " + model.getName() + "      Phone: " + model.getPhone() + "       Age: " + model.getAge());
            } else Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        });
        mapViewModel.listenForChanges((querySnapshot, e) -> {
            for (Marker marker: markerList) marker.remove();            // remove all markers from the map
            for (RemoteRequestModel model: querySnapshot.toObjects(RemoteRequestModel.class)) addMarker(model.getBloodType(),"Name: " + model.getName() + "      Phone: " + model.getPhone() + "       Age: " + model.getAge());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewModel.removeListener();
    }
}
