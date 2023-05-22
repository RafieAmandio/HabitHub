const { pool } = require('../config/config');

// Controller function to create a new habit
const createHabit = async (req, res) => {
  try {
    const { userId, goalId, habitName, habitDescription } = req.body;

    const query = {
      text: 'INSERT INTO habits (user_id, goal_id, habit_name, habit_description) VALUES ($1, $2, $3, $4) RETURNING *',
      values: [userId, goalId, habitName, habitDescription],
    };

    const result = await pool.query(query);

    res.status(201).json(result.rows[0]);
  } catch (error) {
    console.error('Error creating habit:', error);
    res.status(500).json({ error: 'Failed to create habit' });
  }
};

module.exports = {
  createHabit,
}