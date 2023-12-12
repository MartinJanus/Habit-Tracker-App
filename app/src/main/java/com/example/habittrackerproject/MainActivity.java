package com.example.habittrackerproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewHabit viewHabit;
    private FloatingActionButton addHabitButton;
    private HabitListAdapter habitListAdapter;
    private RecyclerView habitRecyclerView;
    private TextView toolbarInfo;
    private TextView toolbarTitle;
    private BottomNavigationView bottomNavigationView;
    private HabitManager habitManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get current date and day with approrpiate format 
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());

        /* Topbar to display current date and day 
         * example - "Monday" and "01-Jan-2024" 
         */
        toolbarInfo = findViewById(R.id.toolbar_info);
        toolbarInfo.setText(currentDate + " ");

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(currentDay);

        /* Bottom Navigation Bar 
         * Location icon takes you to location view 
         * Today icon takes you to main activity
         * Reference: https://www.geeksforgeeks.org/bottomnavigationview-inandroid/
        */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_location) {
                Intent intent = new Intent(MainActivity.this, LocationViewActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_today) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });

        /* Recycler View
         * Finds recycler view and sets up the adapter
         * Layout Manager is set to linear layout - Required for functionality
         */
        habitRecyclerView = findViewById(R.id.habitRecyclerView);
        addHabitButton  = findViewById(R.id.addHabitButton);
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));
        habitManager = new HabitManager(viewHabit);
        habitListAdapter = new HabitListAdapter(this, habitManager);
        habitRecyclerView.setAdapter(habitListAdapter);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Gets all habits from ROOM db and sets them to the adapter
        viewHabit.getAllHabits().observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                habitListAdapter.setHabits(habits);
            }
        });

        // Detailed View - Clicking on a habit item
        habitListAdapter.setOnItemClickListener(new HabitListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Habit habit) {
                Intent intent = new Intent(MainActivity.this, HabitDetailActivity.class);
                intent.putExtra("habitId", habit.getId()); // Pass habit ID
                startActivity(intent);
            }
        });

        /* Deleting Habits - Long Clicking on a habit item 
         * Dialog box to confirm deletion
        */
        habitListAdapter.setOnItemLongClickListener(new HabitListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Habit habit) {
                new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Habit")
                .setMessage("Are you sure you want to delete your Habit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewHabit.delete(habit);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
            }
        });

        // Adding Habits - Clicking on the add habit button
        addHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HabitCreationActivity.class);
                startActivity(intent);
            }
        });
    }
}