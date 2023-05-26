const {pool} = require('../config/config');
const {v4: uuidv4} = require('uuid');

// Controller function to create a new habit

const createHabit = async (req, res) => {
  try {
    const {description, goalId, habitName, startDate} = req.body;
    const habitId = uuidv4();

    // Perform data validation
    if (!description || !goalId || !habitName || !startDate) {
      return res.status(400).json({error: 'Missing required fields'});
    }

    if (!isValidDate(startDate)) {
      return res.status(400).json({error: 'Invalid target date format'});
    }

    // Check if the goal is exist
    const goalQuery = {
      text: `
        SELECT * FROM goals
        WHERE goalid = $1
      `,
      values: [goalId],
    };

    const goalResult = await pool.query(goalQuery);
    if (goalResult.rows.length === 0) {
      return res.status(404).json({error: 'User or Goal not found'});
    }

    // Check if habit already exists
    const habitQuery = {
      text: `
        SELECT * FROM habits
        WHERE habitname = $1
      `,
      values: [habitName],
    };

    const habitResult = await pool.query(habitQuery);
    if (habitResult.rows.length > 0) {
      return res.status(409).json({error: 'Habit already exists'});
    }

    // Create the habit
    const query = {
      text: `
        INSERT INTO habits (habitid, description, goalid, habitname, startdate) 
        VALUES ($1, $2, $3, $4, $5) RETURNING *
      `,
      values: [habitId, description, goalId, habitName, startDate],
    };

    const result = await pool.query(query);

    res.status(201).json(result.rows[0]);
  } catch (error) {
    console.error('Error creating habit:', error);
    res.status(500).json({error: 'Failed to create habit'});
  }
};

const getAllHabitsByUserId = async (req, res) => {
  try {
    const {userId} = req.params;

    const query = {
      text: `
        SELECT habits.*, goals.goalname
        FROM habits
        INNER JOIN goals ON habits.goalid = goals.goalid
        WHERE habits.userid = $1
      `,
      values: [userId],
    };

    const result = await pool.query(query);

    res.json(result.rows);
  } catch (error) {
    console.error('Error fetching habits by user ID:', error);
    res.status(500).json({error: 'Failed to fetch habits'});
  }
};

const getAllHabitsByGoalId = async (req, res) => {
  try {
    const {goalId} = req.params;

    const query = {
      text: `
        SELECT habits.*, users.username
        FROM habits
        INNER JOIN users ON habits.userid = users.userid
        WHERE habits.goalid = $1
      `,
      values: [goalId],
    };

    const result = await pool.query(query);

    res.json(result.rows);
  } catch (error) {
    console.error('Error fetching habits by goal ID:', error);
    res.status(500).json({error: 'Failed to fetch habits'});
  }
};

const updateHabitById = async (req, res) => {
  try {
    const {habitId} = req.params;
    const {description, goalName, targetDate} = req.body;

    const query = {
      text: `
        UPDATE habits
        SET description = $1, goalname = $2, targetdate = $3
        WHERE habitid = $4
        RETURNING *
      `,
      values: [description, goalName, targetDate, habitId],
    };

    const result = await pool.query(query);

    if (result.rows.length === 0) {
      return res.status(404).json({error: 'Habit not found'});
    }

    res.json(result.rows[0]);
  } catch (error) {
    console.error('Error updating habit by ID:', error);
    res.status(500).json({error: 'Failed to update habit'});
  }
};

const deleteHabitById = async (req, res) => {
  try {
    const {habitId} = req.params;

    const query = {
      text: 'DELETE FROM habits WHERE habitid = $1 RETURNING *',
      values: [habitId],
    };

    const result = await pool.query(query);

    if (result.rows.length === 0) {
      return res.status(404).json({error: 'Habit not found'});
    }

    res.json({message: 'Habit deleted successfully'});
  } catch (error) {
    console.error('Error deleting habit by ID:', error);
    res.status(500).json({error: 'Failed to delete habit'});
  }
};

const isValidDate = (dateString) => {
  const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
  return dateRegex.test(dateString);
};

module.exports = {
  createHabit,
  getAllHabitsByUserId,
  getAllHabitsByGoalId,
  updateHabitById,
  deleteHabitById,
};
