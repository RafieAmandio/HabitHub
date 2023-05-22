const express = require('express');
const router = express.Router();

const usersController = require('../controllers/usersController');

// Route to create a new user
router.post('/register', usersController.registerUser);

// Login route
router.post('/login', usersController.loginUser);

// Route to get user by ID
router.get('/get/:id', usersController.getUserById);

// Route to update user by ID
router.put('/update/:id', usersController.updateUserById);

// Route to delete user by ID
router.delete('/delete/:id', usersController.deleteUserById);

module.exports = router;
