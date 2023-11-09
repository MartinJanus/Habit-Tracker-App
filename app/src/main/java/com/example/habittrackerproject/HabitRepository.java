package com.example.habittrackerproject;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//Used to isolate data sources (Room Database) from the rest of the app
public class HabitRepository {
    HabitDAO habitDAO;
    LiveData<List<Habit>> allHabits;

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
