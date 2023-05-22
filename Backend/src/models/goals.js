// goals.js

class Goals {
  constructor(GoalID, UserID, GoalName, Description, TargetDate) {
    this.GoalID = GoalID;
    this.UserID = UserID;
    this.GoalName = GoalName;
    this.Description = Description;
    this.TargetDate = TargetDate;
  }
}

module.exports = Goals;
