package com.example.habittrackerproject;

import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.HabitViewHolder> {

    private HabitManager habitManager;
    private List<Habit> habits = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener onItemLongClickListener;
    private LocationManager locationManager;


    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_list_item, parent, false);
        return new HabitViewHolder(itemView);
    }
    public HabitListAdapter(Context context, HabitManager habitManager) {
        this.habitManager = habitManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    // Setting onClick listeners, CheckBoxes, and TextViews
    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit currentHabit = habits.get(position);
        holder.completedCheckBox.setOnCheckedChangeListener(null);
        holder.habitNameTextView.setText(currentHabit.getHabitName());
        holder.completedCheckBox.setChecked(currentHabit.isCompleted());

        holder.completedCheckBox.setOnCheckedChangeListener(null);
        holder.completedCheckBox.setChecked(currentHabit.isCompleted());

        habitManager.checkIfHabitIsCompleted(currentHabit, holder);


        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentHabit.setIsCompleted(isChecked);
            if (isChecked) {
                habitManager.setHabitAsCompleted(currentHabit, locationManager);
            } else {
                habitManager.setHabitAsNotCompleted(currentHabit);
            }
        });
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
        public CheckBox getCompletedCheckBox() {
            return completedCheckBox;
        }
    }
}