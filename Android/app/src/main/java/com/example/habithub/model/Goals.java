package com.example.habithub.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Goals {

    @SerializedName("goalid")
    private String goalId;

    @SerializedName("userid")
    private String userId;

    @SerializedName("goalname")
    private String goalName;

    @SerializedName("description")
    private String description;

    @SerializedName("targetdate")
    private String targetDate;

    // Getters and Setters

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public String toString() {
        return "Goals{" +
                "goalId='" + goalId + '\'' +
                ", userId='" + userId + '\'' +
                ", goalName='" + goalName + '\'' +
                ", description='" + description + '\'' +
                ", targetDate='" + targetDate + '\'' +
                '}';
    }
}
