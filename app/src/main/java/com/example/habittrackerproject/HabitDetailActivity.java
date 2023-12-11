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
    private TextView habitNameTextView;
    private TextView habitDescriptionTextView;
    private TextView habitLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        habitNameTextView = findViewById(R.id.habitNameTextView);
        habitDescriptionTextView = findViewById(R.id.habitDescriptionTextView);
        habitLocation = findViewById(R.id.habitLocationTextView);
        editHabitButton = findViewById(R.id.editHabitButton);


        viewHabit = new ViewModelProvider(this).get(ViewHabit.class);

        Intent intent = getIntent();
        //fix here
        int habitId = intent.getIntExtra("habitId", -1);
        if (intent != null && intent.hasExtra("habitId")) {
            if (habitId != -1) {
                viewHabit.getHabitById(habitId).observe(this, new Observer<Habit>() {
                    @Override
                    public void onChanged(Habit habit) {
                        if (habit != null) {
                            habitNameTextView.setText(habit.getHabitName());
                            habitDescriptionTextView.setText(habit.getHabitDescription());
                            habitLocation.setText(habit.getLocation());
                         }
                    }
                });
            }
        }

        //  Edit Habit Activity
        editHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitDetailActivity.this, HabitEditActivity.class);
                intent.putExtra("habitId", habitId);
                startActivity(intent);
            }
        });
    }
}
