package com.example.habithub.model;

import java.util.Date;

public class Goals {
    private String goalId;
    private String userId;
    private String goalName;
    private String description;
    private Date targetDate;

    public Goals(String goalId, String userId, String goalName, String description, Date targetDate) {
        this.goalId = goalId;
        this.userId = userId;
        this.goalName = goalName;
        this.description = description;
        this.targetDate = targetDate;
    }

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

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
}
