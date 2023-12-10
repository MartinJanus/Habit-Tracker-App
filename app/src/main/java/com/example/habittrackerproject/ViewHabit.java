package com.example.habittrackerproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewHabit extends AndroidViewModel {

    private HabitRepository habitRepository;

    private LiveData<List<Habit>> allHabits;

    public ViewHabit(@NonNull Application application) {
        super(application);
        habitRepository = new HabitRepository(application);
        allHabits = habitRepository.getAllHabits();
    }
    public void insert(Habit habit){
        habitRepository.insert(habit);
    }
    public void update(Habit habit){
        habitRepository.update(habit);
    }
    public void delete(Habit habit){
        habitRepository.delete(habit);
    }
    public void deleteAllHabits(){
        habitRepository.deleteAllHabits();
    }

    public LiveData<List<Habit>> getAllHabits(){
        return allHabits;
    }

    public LiveData<Habit> getHabitById(int habitId) { return habitRepository.getHabitById(habitId); }

    public LiveData<List<Habit>> getCompletedHabits() { return habitRepository.getCompletedHabits(); }
}
