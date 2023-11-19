package com.example.habittrackerproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class HabitDetailActivity extends AppCompatActivity {

    ViewHabit viewHabit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        TextView habitNameTextView = findViewById(R.id.habitNameTextView);
        TextView habitDescriptionTextView = findViewById(R.id.habitDescriptionTextView);

        viewHabit = new ViewModelProvider(this).get(ViewHabit.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("habitId")) {
            int habitId = intent.getIntExtra("habitId", -1);
            if (habitId != -1) {
                viewHabit.getHabitById(habitId).observe(this, new Observer<Habit>() {
                    @Override
                    public void onChanged(Habit habit) {
                        if (habit != null) {
                            habitNameTextView.setText(habit.getHabitName());
                            habitDescriptionTextView.setText(habit.getHabitName());  //(getHabitDescription)
                         }
                    }
                });
            }
        }
    }
}
