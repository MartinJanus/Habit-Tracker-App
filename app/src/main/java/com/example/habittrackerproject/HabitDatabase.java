package com.example.habittrackerproject;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Habit.class}, version = 1)
public abstract class HabitDatabase extends RoomDatabase {
    //create instance for database class
    private static HabitDatabase instance;
    public abstract HabitDAO habitDAO();

    public static synchronized HabitDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HabitDatabase.class, "habit_database")
                    .fallbackToDestructiveMigration()
//                    .addCallBack(roomCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallBack = onCreate(db) -> {
//        super.onCreate(db);
//        new PopulateDbAsyncTask(instance).execute();
//    };
//
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        PopulateDbAsyncTask(CourseDatabase instance) {
//            Dao dao = instance.Dao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            return null;
//        }
//    }
}
