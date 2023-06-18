package com.example.habithub;

import static com.example.habithub.Home.checkHabit;
import static com.example.habithub.Home.uncheckedHabit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.habithub.model.Habit;

import java.util.ArrayList;

public class HabitAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private ArrayList<Habit> habitList;

    public HabitAdapter(Context context, ArrayList<Habit> habitList) {
        super(context, 0, habitList);
        this.context = context;
        this.habitList = habitList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_habit, parent, false);
            holder = new ViewHolder();
            holder.habitNameTextView = convertView.findViewById(R.id.habitNameTextView);
            holder.daysOfWeekTextView = convertView.findViewById(R.id.daysOfWeekTextView);
            holder.checklistButton = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Habit habit = habitList.get(position);
        holder.habitNameTextView.setText(habit.getHabitName());
        holder.daysOfWeekTextView.setText(habit.getDaysOfWeek());

        //create toast to print days of week
        //Toast.makeText(getContext(), habit.getDaysOfWeek(), Toast.LENGTH_SHORT).show();

        // Set click listener for the checklist button
        holder.checklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle checklist button click
                CheckBox checkBox = (CheckBox) v;
                Habit habit = habitList.get(position);

                if (checkBox.isChecked()) {
                    // Trigger API call when the checkbox is checked
                    checkHabit(habit);
                } else {
                    // Handle unchecking of the checkbox if needed
                    uncheckedHabit(habit);
                }
            }
        });

        return convertView;
    }




    static class ViewHolder {
        TextView habitNameTextView;
        TextView daysOfWeekTextView;
        CheckBox checklistButton;
    }
}

