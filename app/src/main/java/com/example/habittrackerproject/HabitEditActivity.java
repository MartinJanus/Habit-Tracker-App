package com.example.habittrackerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class HabitEditActivity extends AppCompatActivity {
    private EditText habitNameEdit;
    private EditText habitDescEdit;
    private EditText startDateEdit;
    private Button saveChangesButton;
    private Habit habit;
    private ViewHabit viewHabit;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_edit);

        habitNameEdit = findViewById(R.id.HabitNameEdit);
        habitDescEdit = findViewById(R.id.HabitDescEdit);
        startDateEdit = findViewById(R.id.StartDateEdit);
        saveChangesButton = findViewById(R.id.buttonSaveChanges);
        viewHabit = new ViewModelProvider(this).get(ViewHabit.class);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_location) {
                Intent intent = new Intent(HabitEditActivity.this, LocationViewActivity.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.navigation_today) {
                Intent intent = new Intent(HabitEditActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            return true;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("habitId")) {
            int habitId = intent.getIntExtra("habitId", -1);
            if (habitId != -1) {
                viewHabit.getHabitById(habitId).observe(this, new Observer<Habit>() {
                    @Override
                    public void onChanged(Habit habit) {
                        if (habit != null) {
                            HabitEditActivity.this.habit = habit;
                        }
                    }
                });
            }
        }

        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HabitEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);
                                startDateEdit.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(selectedDate.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = habitNameEdit.getText().toString();
                String updatedDescription = habitDescEdit.getText().toString();
                String updatedStartDate = startDateEdit.getText().toString();

                if (habit != null) {
                    habit.setHabitName(updatedName);
                    habit.setHabitDescription(updatedDescription);
                    habit.setStartDate(updatedStartDate);
                    viewHabit.update(habit);
                    Toast.makeText(HabitEditActivity.this, "Habit updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HabitEditActivity.this, "Habit not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
