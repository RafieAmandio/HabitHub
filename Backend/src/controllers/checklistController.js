const { pool } = require('../config/config');

const checkHabit = async (req, res) => {
    try {
      const { userId, habitId } = req.body;
  
      const currentDate = new Date().toISOString().split('T')[0];
  
      // Check if the habit is already checked for the current date
      const existingCheck = await pool.query(
        'SELECT * FROM checked_habits WHERE habit_id = $1 AND checked_date = $2',
        [habitId, currentDate]
      );
  
      if (existingCheck.rows.length === 0) {
        // Insert a new row for the checked habit
        await pool.query('INSERT INTO checked_habits (habit_id, checked, checked_date) VALUES ($1, true, $2)', [
          habitId,
          currentDate,
        ]);
        // Insert a new row in the habit_points table
        await pool.query('INSERT INTO habit_points (user_id, habit_id, points) VALUES ($1, $2, 1)', [userId, habitId]);
      } else {
        // Update the existing row to set checked to true
        await pool.query(
          'UPDATE checked_habits SET checked = true WHERE habit_id = $1 AND checked_date = $2',
          [habitId, currentDate]
        );
        // Check if the habit was previously unchecked
        if (!existingCheck.rows[0].checked) {
          // Insert a new row in the habit_points table
          await pool.query('INSERT INTO habit_points (user_id, habit_id, points) VALUES ($1, $2, 1)', [userId, habitId]);
        }
      }
  
      res.status(200).json({ message: 'Habit checked successfully' });
    } catch (error) {
      console.error('Error checking habit:', error);
      res.status(500).json({ error: 'Failed to check habit' });
    }
  };

const uncheckHabit = async (req, res) => {
    try {
      const { userId, habitId } = req.body;
  
      const currentDate = new Date().toISOString().split('T')[0];
  
      // Update the row to set checked to false for the current date
      await pool.query('UPDATE checked_habits SET checked = false WHERE habit_id = $1 AND checked_date = $2', [
        habitId,
        currentDate,
      ]);
      // Insert a new row in the habit_points table with -1 points
      await pool.query('INSERT INTO habit_points (user_id, habit_id, points) VALUES ($1, $2, -1)', [userId, habitId]);
  
      res.status(200).json({ message: 'Habit unchecked successfully' });
    } catch (error) {
      console.error('Error unchecking habit:', error);
      res.status(500).json({ error: 'Failed to uncheck habit' });
    }
  };
  

module.exports = {
  checkHabit,
  uncheckHabit,
};
