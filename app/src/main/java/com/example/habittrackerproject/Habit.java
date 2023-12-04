package com.example.habittrackerproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "habit_table")
public class Habit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //habit name
    private String habitName;
    private String habitDescription;
    private String startDate;
//    private String Duration;
    private boolean isCompleted;
    private String lastCompletedDate;


    //Constructor Class

    public Habit(String habitName, String habitDescription){
        this.habitName = habitName;
        this.habitDescription = habitDescription;
        this.isCompleted = false;
        this.lastCompletedDate = ""; 
        this.startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    //Getters and Setters
    public String getHabitName(){return habitName;}
    public void setHabitName(String habitName){this.habitName = habitName;}

    public String getHabitDescription(){return habitDescription;}
    public void setHabitDescription(String habitDescription){this.habitDescription = habitDescription;}

    public String getLastCompletedDate() { return lastCompletedDate; }
    public void setLastCompletedDate(String lastCompletedDate) { this.lastCompletedDate = lastCompletedDate; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    @Override
    public String toString() {
        return habitName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
