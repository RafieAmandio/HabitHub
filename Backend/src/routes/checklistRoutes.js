const express = require('express');
const checklistController = require('../controllers/checklistController');
const verifyToken = require('../middleware/authMiddleware');

const router = express.Router();

//Route to check off a habit
router.post('/checkin',verifyToken, checklistController.checkHabit);

router.post('/uncheck',verifyToken, checklistController.uncheckHabit);

module.exports = router;