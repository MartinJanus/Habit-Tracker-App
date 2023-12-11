package com.example.habittrackerproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class LocationViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ViewHabit viewHabit;
    private LiveData<List<Habit>> completedHabits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.habitMap);
        mapFragment.getMapAsync(this);

        completedHabits = viewHabit.getCompletedHabits(); 
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zLevel = 12.0f;

        // For each habit, add a marker to the map
        completedHabits.observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                for (Habit habit : habits) {
                    double latitude = habit.getLatitude();
                    double longitude = habit.getLongitude();
                    if (!Double.isNaN(latitude) && !Double.isNaN(longitude)) {
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(habit.getHabitName()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zLevel));
                    }
                }
            }
        });
    }
}
