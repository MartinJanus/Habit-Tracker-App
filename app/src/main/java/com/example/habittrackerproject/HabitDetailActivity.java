package com.example.habittrackerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class HabitDetailActivity extends AppCompatActivity {

    private ViewHabit viewHabit;
    private Button editHabitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        TextView habitNameTextView = findViewById(R.id.habitNameTextView);
        TextView habitDescriptionTextView = findViewById(R.id.habitDescriptionTextView);
        TextView habitLocation = findViewById(R.id.habitLocationTextView);

        viewHabit = new ViewModelProvider(this).get(ViewHabit.class);

        editHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Intent to go to HabitCreation
                Intent intent = new Intent(HabitDetailActivity.this, HabitEditActivity.class);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("habitId")) {
            int habitId = intent.getIntExtra("habitId", -1);
            if (habitId != -1) {
                viewHabit.getHabitById(habitId).observe(this, new Observer<Habit>() {
                    @Override
                    public void onChanged(Habit habit) {
                        if (habit != null) {
                            habitNameTextView.setText(habit.getHabitName());
                            habitDescriptionTextView.setText(habit.getHabitDescription());  //(getHabitDescription) same as habit name for now
                            habitLocation.setText(habit.getLocation());
                         }
                    }
                });
            }
        }
    }
}
