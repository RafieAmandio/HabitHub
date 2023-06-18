package com.example.habithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habithub.model.Goals;

import java.util.List;

public class GoalsAdapter extends ArrayAdapter<Goals> {
    private Context mContext;
    private List<Goals> mGoalList;

    public GoalsAdapter(Context context, List<Goals> goalList) {
        super(context, 0, goalList);
        mContext = context;
        mGoalList = goalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.item_goals, parent, false);
        }

        Goals currentGoal = mGoalList.get(position);

        TextView goalNameTextView = listItemView.findViewById(R.id.goalsname);
        TextView goalDescriptionTextView = listItemView.findViewById(R.id.desc);
        TextView goalDateTextView = listItemView.findViewById(R.id.goaldate);

        goalNameTextView.setText(currentGoal.getGoalName());
        goalDescriptionTextView.setText(currentGoal.getDescription());
        goalDateTextView.setText(String.valueOf(currentGoal.getTargetDate()));

        return listItemView;
    }
}
