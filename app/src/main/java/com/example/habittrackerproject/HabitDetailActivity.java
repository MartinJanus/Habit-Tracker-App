package com.example.habittrackerproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HabitDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        TextView habitNameTextView = findViewById(R.id.habitNameTextView);
        TextView habitDescriptionTextView = findViewById(R.id.habitDescriptionTextView);

        //Get data passed from previous activity

        String habitName = getIntent().getStringExtra("habitName");
        String habitDescription = getIntent().getStringExtra("habitDescription");

        //Set habit details in TextView

        habitNameTextView.setText(habitName);
        habitDescriptionTextView.setText(habitDescription);
    }
}
