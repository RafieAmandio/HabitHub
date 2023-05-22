// habits.js

class Habits {
  constructor(HabitID, GoalID, HabitName, StartDate, Description) {
    this.HabitID = HabitID;
    this.GoalID = GoalID;
    this.HabitName = HabitName;
    this.StartDate = StartDate;
    this.Description = Description;
  }
}

module.exports = Habits;
