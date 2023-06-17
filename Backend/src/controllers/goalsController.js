const {pool} = require('../config/config');
const {v4: uuidv4} = require('uuid');

// Controller function to get all goals
const getAllGoalsByUserId = async (req, res) => {
  try {
    const {userid} = req.body;
    console.log(userid);
    const query = {
      text: 'SELECT * FROM goals WHERE userid = $1',
      values: [userid],
    };
    const result = await pool.query(query);
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

// Controller function to get a goal by goalid
const getGoalById = async (req, res) => {
  try {
    const {goalid} = req.params;
    const query = {
      text: 'SELECT * FROM goals WHERE goalid = $1',
      values: [goalid],
    };
    const result = await pool.query(query);
    if (result.rows.length === 0) {
      res.status(404).json({message: 'Goal not found'});
    } else {
      res.json(result.rows[0]);
    }
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

// Controller function to create a new goal
const createGoal = async (req, res) => {
  try {
    // Extract the required fields from the request body
    const {description, goalName, targetDate, userId} = req.body;
    console.log(req.body);
    // Perform data validation
    if (!description || !goalName || !targetDate || !userId) {
      //print whats missing
      


      console.log('Missing required fields');
      return res.status(400).json({error: 'Missing required fields'});
    }

    // Validate data types and formats
    if (
      typeof description !== 'string' ||
      typeof goalName !== 'string' ||
      typeof userId !== 'string'
    ) {
      console.log('Invalid data types');
      return res.status(400).json({error: 'Invalid data types'});
    }

    if (!isValidDate(targetDate)) {
      console.log('Invalid target date format');
      return res.status(400).json({error: 'Invalid target date format'});
    }

    const goalId = uuidv4(); // Generate a unique ID for the goal

    // Insert the goal into the database
    const query = {
      text: 'INSERT INTO goals (goalid, description, goalname, targetdate, userid) VALUES ($1, $2, $3, $4, $5) RETURNING *',
      values: [goalId, description, goalName, targetDate, userId],
    };

    const result = await pool.query(query);
    console.log("Success " + result.rows[0]);
    res.status(201).json(result.rows[0]);
  } catch (error) {
    console.error('Error creating goal:', error);
    res.status(500).json({error: 'Failed to create goal'});
  }
};

const updateGoalById = async (req, res) => {
  try {
    const {goalId} = req.params;
    const {description, goalName, targetDate, userId} = req.body;

    // Perform data validation
    if (!description || !goalName || !targetDate || !userId) {
      return res.status(400).json({error: 'Missing required fields'});
    }

    // Validate data types and formats
    if (
      typeof description !== 'string' ||
      typeof goalName !== 'string' ||
      typeof userId !== 'string'
    ) {
      return res.status(400).json({error: 'Invalid data types'});
    }

    if (!isValidDate(targetDate)) {
      return res.status(400).json({error: 'Invalid target date format'});
    }

    // Update the goal in the database
    const query = {
      text: 'UPDATE goals SET description = $1, goalname = $2, targetdate = $3, userid = $4 WHERE goalid = $5 RETURNING *',
      values: [description, goalName, targetDate, userId, goalId],
    };

    const result = await pool.query(query);

    if (result.rows.length === 0) {
      return res.status(404).json({message: 'Goal not found'});
    }

    res.json(result.rows[0]);
  } catch (error) {
    console.error('Error updating goal:', error);
    res.status(500).json({error: 'Failed to update goal'});
  }
};

// Controller function to delete a goal by goal ID
const deleteGoalById = async (req, res) => {
  try {
    const {goalid} = req.params;

    const query = {
      text: 'DELETE FROM goals WHERE goalid = $1',
      values: [goalid],
    };

    const result = await pool.query(query);

    if (result.rowCount === 0) {
      res.status(404).json({message: 'Goal not found'});
    } else {
      res.json({message: 'Goal deleted successfully'});
    }
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

const getAllGoals = async (req, res) => {
  try {
    const {userid} = req.params;

    const query = {
      text: 'SELECT * FROM goals WHERE userid = $1',
      values: [userid],
    };

    const result = await pool.query(query);

    res.json(result.rows);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

const isValidDate = (dateString) => {
  const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
  return dateRegex.test(dateString);
};

module.exports = {
  getAllGoals,
  getAllGoalsByUserId,
  getGoalById,
  createGoal,
  updateGoalById,
  deleteGoalById,
};
