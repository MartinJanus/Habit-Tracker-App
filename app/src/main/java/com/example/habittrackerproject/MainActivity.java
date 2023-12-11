package com.example.habittrackerproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private String currentDate;
    private String currentDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Date and Day
        currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());

        //Top Bar (Fix for habits completed)
        toolbarInfo = findViewById(R.id.toolbar_info);
        toolbarInfo.setText(currentDate + "  "); //+ completedHabits + "/" + totalHabits + " Habits Completed");

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(currentDay);

        //Bottom Nav Bar
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
            } else if (item.getItemId() == R.id.navigation_calendar) {
                Intent intent = new Intent(MainActivity.this, CalendarViewActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });

        // Recycler View
        habitRecyclerView = findViewById(R.id.habitRecyclerView);
        addHabitButton  = findViewById(R.id.addHabitButton);
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));
        Handler mainHandler = new Handler(Looper.getMainLooper());

        habitListAdapter = new HabitListAdapter(this, mainHandler, viewHabit);
        habitRecyclerView.setAdapter(habitListAdapter);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewHabit.getAllHabits().observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                habitListAdapter.setHabits(habits);
            }
        });

        //Detailed View, Clicking on the Item.
        habitListAdapter.setOnItemClickListener(new HabitListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Habit habit) {
                Intent intent = new Intent(MainActivity.this, HabitDetailActivity.class);
                intent.putExtra("habitId", habit.getId()); // Pass habit ID
                startActivity(intent);
            }
        });
        
        //Deleting Habits
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

        // Adding Habits
        addHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HabitCreationActivity.class);
                startActivity(intent);
            }
        });
    }
}