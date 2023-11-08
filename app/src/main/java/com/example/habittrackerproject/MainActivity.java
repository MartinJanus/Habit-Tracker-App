package com.example.habittrackerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText editTextHabitName;
    Button buttonCreateHabit;
    HabitDatabase habitDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextHabitName = findViewById(R.id.editTextHabitName);
        buttonCreateHabit = findViewById(R.id.buttonCreateHabit);

        habitDatabase = HabitDatabase.getInstance(this);

        buttonCreateHabit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String habitName = editTextHabitName.getText().toString();
                if(!habitName.isEmpty()) {
                    Habit habit = new Habit(habitName);
                    habit.setHabitName(habitName);
                    insertHabit(habit);
                } else {
                    Toast.makeText(MainActivity.this,"Please Enter a habit", Toast.LENGTH_SHORT).show();
                }

                //Get other habit details from editTexts and save them into database

                //display toast to say habit created

                Toast.makeText(MainActivity.this, "Habit Created: " + habitName, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "Habit Created: " + habit.getHabitName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}