package com.example.habittrackerproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class HabitEditActivity extends AppCompatActivity {
    private EditText habitNameEdit;
    private EditText habitDescEdit;
    private Button saveChangesButton;
    private Habit habit;
    private ViewHabit viewHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_edit);

        habitNameEdit = findViewById(R.id.HabitNameEdit);
        habitDescEdit = findViewById(R.id.HabitDescEdit);
        saveChangesButton = findViewById(R.id.buttonSaveChanges);
        viewHabit = new ViewModelProvider(this).get(ViewHabit.class);

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
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = habitNameEdit.getText().toString();
                String updatedDescription = habitDescEdit.getText().toString();

                if (habit != null) {
                    habit.setHabitName(updatedName);
                    habit.setHabitDescription(updatedDescription);
                    viewHabit.update(habit);
                    Toast.makeText(HabitEditActivity.this, "Habit updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HabitEditActivity.this, "Habit not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
