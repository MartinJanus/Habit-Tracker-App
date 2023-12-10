package com.example.habittrackerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewHabit viewHabit;
    private Button addHabitButton;
    private HabitDatabase habitDatabase;
    private HabitListAdapter habitListAdapter;
    private RecyclerView habitRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Top Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Todays Date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

//        int completedHabits = getCompletedHabits();
//        int totalHabits = getTotalHabits();

        TextView toolbarInfo = findViewById(R.id.toolbar_info);
        toolbarInfo.setText(currentDate + "  "); //+ completedHabits + "/" + totalHabits + " Habits Completed");


        habitDatabase = HabitDatabase.getInstance(this);
        habitRecyclerView = findViewById(R.id.habitRecyclerView);
        addHabitButton  = findViewById(R.id.addHabitButton);
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));
        
        Handler mainHandler = new Handler(Looper.getMainLooper());
        HabitListAdapter adapter = new HabitListAdapter(this, mainHandler, habitDatabase);


        habitListAdapter = new HabitListAdapter(this, mainHandler, habitDatabase);
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
                viewHabit.delete(habit);
            }
        });

//        //Checkbox for Completed Habits
//        habitListAdapter.setOnItemCheckedChangeListener(new HabitListAdapter.OnItemCheckedChangeListener() {
//            @Override
//            public void onItemCheckedChange(Habit habit) {
//                new Thread(() -> habitDatabase.habitDAO().update(habit)).start();
//            }
//        });

        // Adding Habits
        addHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Intent to go to HabitCreation
                Intent intent = new Intent(MainActivity.this, HabitCreationActivity.class);
                startActivity(intent);

            }
        });
    }
}