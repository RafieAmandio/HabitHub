package com.example.habithub.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Habit {
    @SerializedName("habitid")
    private UUID habitId;
    @SerializedName("goalid")
    private UUID goalId;
    @SerializedName("habitname")
    private String habitName;
    @SerializedName("description")
    private String description;
    @SerializedName("startdate")
    private String startDate;

    @SerializedName("daysofweek")
    private String daysofweek;

    public UUID getHabitId() {
        return habitId;
    }

    public void setHabitId(UUID habitId) {
        this.habitId = habitId;
    }

    public UUID getGoalId() {
        return goalId;
    }

    public void setGoalId(UUID goalId) {
        this.goalId = goalId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDaysOfWeek() {
        return daysofweek;
    }

    //create to string
    @Override
    public String toString() {
        return "Habit{" +
                "habitId=" + habitId +
                ", goalId=" + goalId +
                ", habitName='" + habitName + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", daysofweek='" + daysofweek + '\'' +
                '}';
    }
}
