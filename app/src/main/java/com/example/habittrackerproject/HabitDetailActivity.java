package com.example.habittrackerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HabitDetailActivity extends AppCompatActivity {

    private ViewHabit viewHabit;
    private Button editHabitButton;
    private TextView habitNameTextView;
    private TextView habitDescriptionTextView;
    private TextView habitStartDate;
    private TextView habitStreakTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);

        habitNameTextView = findViewById(R.id.habitNameTextView);
        habitDescriptionTextView = findViewById(R.id.habitDescriptionTextView);
        habitStartDate = findViewById(R.id.habitStartDateTextView);
        habitStreakTextView = findViewById(R.id.habitStreakTextView);

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
                            habitStartDate.setText(habit.getStartDate());
                            habitStreakTextView.setText(String.valueOf(habit.getStreak()));
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

//        //Bottom Nav Bar
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            if(item.getItemId() == R.id.navigation_location) {
//                Intent intent = new Intent(MainActivity.this, LocationViewActivity.class);
//                startActivity(intent);
//                return true;
//            } else if (item.getItemId() == R.id.navigation_today) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                return true;
//            } else if (item.getItemId() == R.id.navigation_calendar) {
//                Intent intent = new Intent(MainActivity.this, CalendarViewActivity.class);
//                startActivity(intent);
//                return true;
//            }
//            return true;
//        });
    }
}
