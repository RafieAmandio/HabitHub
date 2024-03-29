const express = require('express');
const goalsController = require('../controllers/goalsController');
const verifyToken = require('../middleware/authMiddleware');

const router = express.Router();

// Route to get all goals
router.get('/', goalsController.getAllGoals);

// Route to get all goals by userid
router.post('/user',verifyToken, goalsController.getAllGoalsByUserId);

// Route to get a goal by goalid
router.get('/goalid/:goalid', goalsController.getGoalById);

// Route to create a new goal
router.post('/create',verifyToken, goalsController.createGoal);

// Route to update a goal by goalid
router.put('/update/:goalid', goalsController.updateGoalById);

// Route to delete a goal by goalid
router.delete('/delete/:goalid', goalsController.deleteGoalById);

module.exports = router;
