//package com.example.habittrackerproject;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class HabitCreation extends MainActivity {
//    EditText editTextHabitName;
//    Button buttonCreateHabit;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        editTextHabitName = findViewById(R.id.editTextHabitName);
//        buttonCreateHabit = findViewById(R.id.buttonCreateHabit);
//
//        buttonCreateHabit.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                String habitName = editTextHabitName.getText().toString();
//
//                //Get other habit details from editTexts and save them into database
//
//                //display toast to say habit created
//
//                Toast.makeText(HabitCreation.this, "Habit Created: " + habitName, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
