package com.example.habittrackerproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HabitCreationActivity extends AppCompatActivity {

    private EditText editTextHabitName;
    private EditText editTextHabitDescription;
    private HabitDatabase habitDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_create);

        editTextHabitName = findViewById(R.id.editTextHabitName);
        editTextHabitDescription = findViewById(R.id.editTextDescription);
        Button buttonCreateHabit = findViewById(R.id.buttonCreateHabit);

        habitDatabase = HabitDatabase.getInstance(this);

        buttonCreateHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = editTextHabitName.getText().toString();
                String habitDescription = editTextHabitDescription.getText().toString();
                if (!habitName.isEmpty()) {
                    Habit habit = new Habit(habitName,habitDescription);
                    habit.setHabitName(habitName);
                    habit.setHabitDescription(habitDescription);
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