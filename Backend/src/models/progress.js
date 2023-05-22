// progress.js

class Progress {
  constructor(ProgressID, HabitID, SuccessfulDays, TargetDays) {
    this.ProgressID = ProgressID;
    this.HabitID = HabitID;
    this.SuccessfulDays = SuccessfulDays;
    this.TargetDays = TargetDays;
  }
}

module.exports = Progress;
