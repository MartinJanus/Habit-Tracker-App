package com.example.habittrackerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText editTextHabitName;
    Button addHabitButton;
    HabitDatabase habitDatabase;

    ListView habitListView;
    List<Habit> habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitDatabase = HabitDatabase.getInstance(this);
        habitListView = findViewById(R.id.habitListView);
        addHabitButton  = findViewById(R.id.addHabitButton);

        addHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Intent to go to HabitCreation
                Intent intent = new Intent(MainActivity.this, HabitCreationActivity.class);
                startActivity(intent);

            }
        });

        loadHabits();
    }



    private void loadHabits() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //habitList = habitDatabase.habitDAO().getAllHabits(); //fix
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Create an adapter to display habit names in the ListView
                        ArrayAdapter<Habit> adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1, habitList);
                        habitListView.setAdapter(adapter);
                    }
                });
            }
        });
    }
}