package com.example.habittrackerproject;

import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* Purpose to seperate logic from HabitListAdapter - which displays */
public class HabitManager {
    private ViewHabit viewHabit;
    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Handler to update UI
    public HabitManager(ViewHabit viewHabit) {
        this.viewHabit = viewHabit;
    }

    // Thread to check if habit is completed
    public void checkIfHabitIsCompleted(Habit currentHabit, HabitListAdapter.HabitViewHolder holder) {
        new Thread(() -> {
            try {
                Thread.sleep(60000); // 1 minute

                // updates UI - if lastCompletedDate differs from the Current date, checkbox UI is reset
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

    // If marked as not completed - reset streak and update database
    public void setHabitAsNotCompleted(Habit currentHabit) {
        currentHabit.setStreak(0);
        currentHabit.setIsCompleted(false);
        Log.d("HabitTracker", "Habit " + currentHabit.getHabitName() + " is completed: " + false);
        viewHabit.update(currentHabit);
    }
    // If marked as completed - update streak (if completed yesterday)
    public void setHabitAsCompleted(Habit currentHabit, LocationManager locationManager) {
        currentHabit.setIsCompleted(true);
        if (isDateYesterday(currentHabit.getLastCompletedDate())) {
            currentHabit.setStreak(currentHabit.getStreak() + 1);
        } else {
            currentHabit.setStreak(1);
        }

        // Logs its completed
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentHabit.setLastCompletedDate(currentDate);
        Log.d("HabitTracker", "Habit " + currentHabit.getHabitName() + " is completed: " + true);
        // Location is logged and updated into database to be displayed 
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
    // just to check if date is yesterday
    private boolean isDateYesterday(String date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(cal.getTime());
    
        return date.equals(yesterday);
    }
}