package com.example.habittrackerproject;

import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class HabitManager {
    private ViewHabit viewHabit;
    private Handler mainHandler = new Handler(Looper.getMainLooper());


    public HabitManager(ViewHabit viewHabit) {
        this.viewHabit = viewHabit;
    }
    public void checkIfHabitIsCompleted(Habit currentHabit, HabitListAdapter.HabitViewHolder holder) {
        new Thread(() -> {
            try {
                Thread.sleep(60000); // 1 minute

                mainHandler.post(() -> {
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    if (!currentHabit.getLastCompletedDate().equals(currentDate)) {
                        currentHabit.setIsCompleted(false);
                        // Update the checkbox in the UI
                        holder.getCompletedCheckBox().setChecked(false);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void setHabitAsCompleted(Habit currentHabit, LocationManager locationManager) {
        currentHabit.setIsCompleted(true);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentHabit.setLastCompletedDate(currentDate);
        Log.d("HabitTracker", "Habit " + currentHabit.getHabitName() + " is completed: " + true);

        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                currentHabit.setLatitude(latitude);
                currentHabit.setLongitude(longitude);
                Log.d("HabitTracker", "Habit completed at location: " + location.toString());
                viewHabit.update(currentHabit);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}