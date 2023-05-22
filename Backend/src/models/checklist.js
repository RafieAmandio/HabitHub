// checklist.js

/**
 * Checklist class
 * @class
 * @param {number} ChecklistID - The ID of the checklist
 * @param {number} HabitID - The ID of the habit
 * @param {date} Date - The date of the checklist
 * @param {boolean} Completed - Whether the checklist is completed or not
 * @return {void}
 * @example <caption>Example usage of Checklist class.</caption>
 * const checklist = new Checklist(1, 1, '2020-01-01', true);
 * console.log(checklist);
 */
class Checklist {
  constructor(ChecklistID, HabitID, Date, Completed) {
    this.ChecklistID = ChecklistID;
    this.HabitID = HabitID;
    this.Date = Date;
    this.Completed = Completed;
  }
}

module.exports = Checklist;
