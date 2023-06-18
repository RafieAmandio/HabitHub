const express = require('express');
const router = express.Router();

const habitsController = require('../controllers/habitsController');
const verifyToken = require('../middleware/authMiddleware');

// Route to create a new habit
router.post('/create', habitsController.createHabit);

// Route to get all habits for a specific user
router.post('/getbyuser',verifyToken, habitsController.getAllHabitsByUserId);

// Route to get a specific habit by ID
// router.get('/getbyid/:habitId', habitsController.getHabitById);

// Route to get all habits for a specific goal
router.get('/getbygoal/:goalId', habitsController.getAllHabitsByGoalId);

// Route to update a specific habit by ID
router.put('/update/:habitId', habitsController.updateHabitById);

// Route to delete a specific habit by ID
router.delete('/delete/:habitId', habitsController.deleteHabitById);

module.exports = router;
