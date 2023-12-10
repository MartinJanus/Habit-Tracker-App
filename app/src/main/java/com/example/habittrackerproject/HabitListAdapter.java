package com.example.habittrackerproject;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.HabitViewHolder> {

    private List<Habit> habits = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener onItemLongClickListener;
    private CheckBox completedCheckBox;
    private HabitDatabase habitDatabase;
    private OnItemCheckedChangeListener onItemCheckedChangeListener;

    private Handler mainHandler;



    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list_item, parent, false);
        return new HabitViewHolder(itemView);
    }
    public HabitListAdapter(Handler mainHandler, HabitDatabase habitDatabase) {
        this.mainHandler = mainHandler;
        this.habitDatabase = habitDatabase;
    }



    public interface OnItemCheckedChangeListener {
        void onItemCheckedChange(Habit habit);
    }


    // Setting onClick listeners, CheckBoxes, and TextViews
    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit currentHabit = habits.get(position);
        holder.habitNameTextView.setText(currentHabit.getHabitName());
        holder.completedCheckBox.setChecked(currentHabit.isCompleted());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(habits.get(adapterPosition));
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                int adapterPosition = holder.getAdapterPosition();
                if (onItemLongClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onItemLongClickListener.onItemLongClick(habits.get(adapterPosition));
                }
                return true;
            }
        });

        // thread to check time to see if habit is completed
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Sleep for 1 minute
        
                    mainHandler.post(() -> {
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        if (!currentHabit.getLastCompletedDate().equals(currentDate)) {
                            currentHabit.setIsCompleted(false);
                            // Update the checkbox in the UI
                            holder.completedCheckBox.setChecked(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //Setting date for habit completion
        holder.completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentHabit.setIsCompleted(true);
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    currentHabit.setLastCompletedDate(currentDate);
                    Log.d("HabitTracker", "Habit " + currentHabit.getHabitName() + " is completed: " + isChecked);
                } else {
                    currentHabit.setIsCompleted(false);
                }
                // Update the habit in the database
                new Thread(() -> {
                    habitDatabase.habitDAO().update(currentHabit);
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Habit habit);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Habit habit);
    }
//
//    interface OnItemCheckedChangeListener {
//        void onItemCheckedChange(Habit habit);
//    }
//
//    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
//        this.onItemCheckedChangeListener = listener;
//    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {
        private TextView habitNameTextView;

        private CheckBox completedCheckBox;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitNameTextView = itemView.findViewById(R.id.habitNameTextView);
            completedCheckBox = itemView.findViewById(R.id.completionCheckbox);
        }
    }
}