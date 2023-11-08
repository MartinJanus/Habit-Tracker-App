package com.example.habittrackerproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "habit_table")
public class Habit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //habit name
    private String habitName;
//    private String habitDescription;
//    private String startDate;
//    private String Duration;


    //Constructor Class

    public Habit(String habitName){
        this.habitName = habitName;
    }

    //Getters and Setters
    public void setHabitName(String habitName){this.habitName = habitName;}
    public String getHabitName(){return habitName;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
