package com.example.habittrackerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* Activity for user to create their habits */
public class HabitCreationActivity extends AppCompatActivity {
    private EditText editTextHabitName;
    private EditText editTextHabitDescription;
    private ViewHabit viewHabit;
    private EditText editTextStartDate;
    private Button buttonCreateHabit;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_create);

        editTextHabitName = findViewById(R.id.editTextHabitName);
        editTextHabitDescription = findViewById(R.id.editTextDescription);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        buttonCreateHabit = findViewById(R.id.buttonCreateHabit);

        // View Habit to access database
        viewHabit = new ViewModelProvider(this).get((ViewHabit.class));

        // Bottom Navigation View - Consistent across all activities
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_location) {
                Intent intent = new Intent(HabitCreationActivity.this, LocationViewActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_today) {
                Intent intent = new Intent(HabitCreationActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });

        /* Start Date Selector  
         * Get current date and day with approrpiate format
         * Sets Text to editTextStartDate
        */
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HabitCreationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);
                                editTextStartDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(selectedDate.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        /*Habit Creation Button
         * Checks if habit name is empty - if not then creates habit
         * Toast message to confirm creation 
        */
        buttonCreateHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = editTextHabitName.getText().toString();
                String habitDescription = editTextHabitDescription.getText().toString();
                String startDate = editTextStartDate.getText().toString();

                if (!habitName.isEmpty()) {
                    Habit habit = new Habit(habitName,habitDescription);
                    habit.setHabitName(habitName);
                    habit.setHabitDescription(habitDescription);
                    habit.setStartDate(startDate);
                    viewHabit.insert(habit);
                    Toast.makeText(HabitCreationActivity.this, "Habit Created: " + habit.getHabitName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HabitCreationActivity.this, "Please enter a habit name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}