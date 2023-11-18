package com.example.habittrackerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewHabit viewHabit;
    EditText editTextHabitName;
    Button addHabitButton;
    HabitDatabase habitDatabase;

    ListView habitListView;
    List<Habit> habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitDatabase = HabitDatabase.getInstance(this);
        habitListView = findViewById(R.id.habitListView);
        addHabitButton  = findViewById(R.id.addHabitButton);


        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));

        viewHabit.getAllHabits().observe(this, new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habits) {
                ArrayAdapter<Habit> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, habits);
                habitListView.setAdapter(adapter);
            }
        });

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