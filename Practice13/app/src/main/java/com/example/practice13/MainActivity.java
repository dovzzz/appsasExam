package com.example.practice13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // AIzaSyB9So4DPOIQHHPDRCS_PoilNJkXcCMwgCo - API key
        getLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng kaunas = new LatLng(54.898521, 23.903597);
        LatLng barakas = new LatLng(54.90338767455378, 23.96025154074051);

        // ======================================================
        // current location
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        double userLat = lastKnownLocation.getLatitude();
        double userLong = lastKnownLocation.getLongitude();
        LatLng myLocation = new LatLng(userLat, userLong);

        // ======================================================


        googleMap.addMarker(new MarkerOptions().position(kaunas).title("Marker in Kaunas"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(kaunas, 12.0f));
//        googleMap.addMarker(new MarkerOptions().position(myLocation).title("Marker"));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12.0f));
        googleMap.addMarker(new MarkerOptions().position(barakas).title("Barakas"));


        // ======================================================
        // draw territory with polyline
        googleMap.addPolyline(new PolylineOptions().add(
                new LatLng(54.900785, 23.931606),
                new LatLng(54.904198, 23.949260),
                new LatLng(54.899247, 23.950304),
                new LatLng(54.895458, 23.948804),
                new LatLng(54.895421, 23.944302),
                new LatLng(54.890093, 23.938888),
                new LatLng(54.895833, 23.935430),
                new LatLng(54.900785, 23.931606)).width(5).color(Color.BLUE));

        // ======================================================
        // draw territory with circle
        googleMap.addCircle(new CircleOptions().center(new LatLng(54.918759, 23.934141)).
                radius(1000).strokeColor(R.color.purple_500).fillColor(R.color.color1_transparent).strokeWidth(5));

        // ======================================================
        // add market on click position
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

        // recreates reruns activity
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                recreate();
            }
        });
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}
