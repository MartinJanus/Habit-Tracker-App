package com.example.habittrackerproject;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HabitCreationActivity extends AppCompatActivity {

    private EditText editTextHabitName;
    private EditText editTextHabitDescription;
    private HabitDatabase habitDatabase;
    private EditText editTextStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_create);

        editTextHabitName = findViewById(R.id.editTextHabitName);
        editTextHabitDescription = findViewById(R.id.editTextDescription);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        Button buttonCreateHabit = findViewById(R.id.buttonCreateHabit);

        habitDatabase = HabitDatabase.getInstance(this);

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
                    insertHabit(habit);
                } else {
                    Toast.makeText(HabitCreationActivity.this, "Please enter a habit name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertHabit(Habit habit) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                habitDatabase.habitDAO().insert(habit);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HabitCreationActivity.this, "Habit Created: " + habit.getHabitName(), Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to another activity or update UI
                    }
                });
            }
        });
    }
}