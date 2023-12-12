package com.example.habittrackerproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/* Activity for user to view their habits on a map 
 * Reference: https://www.geeksforgeeks.org/google-maps-in-android/
*/
public class LocationViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ViewHabit viewHabit;
    private LiveData<List<Habit>> completedHabits;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));

        // Get map fragment and load map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.habitMap);
        mapFragment.getMapAsync(this);

        completedHabits = viewHabit.getCompletedHabits();

        // Bottom Navigation View - Consistent across all activities
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_location) {
                Intent intent = new Intent(LocationViewActivity.this, LocationViewActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_today) {
                Intent intent = new Intent(LocationViewActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zLevel = 12.0f; // Zoom level for map

        // For each habit, add a marker to the map
        completedHabits.observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                for (Habit habit : habits) {
                    double latitude = habit.getLatitude();
                    double longitude = habit.getLongitude();
                    // checks if latitude and longitude are valid
                    if (!Double.isNaN(latitude) && !Double.isNaN(longitude)) {
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(location).title(habit.getHabitName())); // marker contains title of habit name
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zLevel)); // Zooms in on marker
                    }
                }
            }
        });
    }
}
