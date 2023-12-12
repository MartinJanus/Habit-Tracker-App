package com.example.habittrackerproject;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// Reference: https://www.geeksforgeeks.org/how-to-perform-crud-operations-in-room-database-in-android/
// Repository to access the DAO which accesses the Database 
public class HabitRepository {
    private HabitDAO habitDAO;
    private LiveData<List<Habit>> allHabits;

    public HabitRepository(Application application) {
        HabitDatabase database = HabitDatabase.getInstance(application);
        habitDAO = database.habitDAO();
        allHabits = habitDAO.getAllHabits(); // fix
    }

    //method to insert data into database
    public void insert(Habit habit) {
        new InsertHabitAsyncTask(habitDAO).execute(habit);
    }

    //update data
    public void update(Habit habit) {
        new UpdateHabitAsyncTask(habitDAO).execute(habit);
    }

    //delete data
    public void delete(Habit habit) {
        new DeleteHabitAsyncTask(habitDAO).execute(habit);
    }

    //delete all data
    public void deleteAllHabits() {
        new DeleteAllHabitsAsyncTask(habitDAO).execute();
    }

    //read habits
    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public LiveData<Habit> getHabitById(int habitId) { return habitDAO.getHabitById(habitId); }

    public LiveData<List<Habit>> getCompletedHabits() { return habitDAO.getCompletedHabits(); }

    private static class InsertHabitAsyncTask extends AsyncTask<Habit, Void, Void> {
        private HabitDAO habitDAO;

        private InsertHabitAsyncTask(HabitDAO habitDAO) {
            this.habitDAO = habitDAO;
        }

        @Override
        protected Void doInBackground(Habit... habits) {
            habitDAO.insert(habits[0]);
            return null;
        }
    }
    private static class UpdateHabitAsyncTask extends AsyncTask<Habit, Void, Void> {
        private HabitDAO habitDAO;

        private UpdateHabitAsyncTask(HabitDAO habitDAO) {
            this.habitDAO = habitDAO;
        }

        @Override
        protected Void doInBackground(Habit... habits) {
            habitDAO.update(habits[0]);
            return null;
        }
    }

    private static class DeleteHabitAsyncTask extends AsyncTask<Habit, Void, Void> {
        private HabitDAO habitDAO;

        private DeleteHabitAsyncTask(HabitDAO habitDAO) {
            this.habitDAO = habitDAO;
        }

        @Override
        protected Void doInBackground(Habit... habits) {
            habitDAO.delete(habits[0]);
            return null;
        }
    }

    private static class DeleteAllHabitsAsyncTask extends AsyncTask<Void, Void, Void> {
        private HabitDAO habitDAO;

        private DeleteAllHabitsAsyncTask(HabitDAO habitDAO) {
            this.habitDAO = habitDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            habitDAO.deleteAllHabits();
            return null;
        }
    }

}
