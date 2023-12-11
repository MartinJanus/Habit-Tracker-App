package com.example.habittrackerproject;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@androidx.room.Dao
public interface HabitDAO {

    //adding data
    @Insert
    void insert(Habit habit);

    //updating data
    @Update
    void update(Habit habit);

    //deleting habits
    @Delete
    void delete(Habit habit);

    //deleting all habits
    @Query("DELETE FROM habit_table")
    void deleteAllHabits();

    // to read all habits in the database (probably to order by habit priority or name)
    @Query("SELECT * FROM habit_table")
    LiveData<List<Habit>> getAllHabits();

    // Query for ID
    @Query("SELECT * FROM habit_table WHERE id = :habitId")
    LiveData<Habit> getHabitById(int habitId);

    // Query for completed habits
    @Query("SELECT * FROM habit_table WHERE isCompleted = 1")
    LiveData<List<Habit>> getCompletedHabits();
}
