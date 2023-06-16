const {pool} = require('../config/config');
const {v4: uuidv4} = require('uuid');
const bcrypt = require('bcrypt');

// Controller function to create a new user
const registerUser = async (req, res) => {
  try {
    console.log("Register User : \n" + req.body);
    const {username, email, password, gender} = req.body;

    const emailCheckQuery = {
      text: 'SELECT * FROM users WHERE email = $1',
      values: [email],
    };

    const emailCheckResult = await pool.query(emailCheckQuery);

    if (emailCheckResult.rows.length > 0) {
      // Email is already registered, return an error response
      return res.status(400).json({error: 'Email is already registered'});
    }

    const id = uuidv4();

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Use parameterized query to prevent SQL injection
    const query = {
      // eslint-disable-next-line max-len
      text: 'INSERT INTO users (userid, username, email, password, gender) VALUES ($1, $2, $3, $4, $5) RETURNING *',
      values: [id, username, email, hashedPassword, gender],
    };

    const result = await pool.query(query);

    res.status(201).json(result.rows[0]);
  } catch (error) {
    console.error('Error creating user:', error);
    res.status(500).json({error: 'Failed to create user'});
  }
};

// Login user
const loginUser = async (req, res) => {
  try {
    const {email, password} = req.body;

    // Use parameterized query to prevent SQL injection
    const query = {
      text: 'SELECT * FROM users WHERE email = $1',
      values: [email],
    };

    const result = await pool.query(query);

    if (result.rows.length > 0) {
      const user = result.rows[0];

      // Compare the provided password with the stored hashed password
      const match = await bcrypt.compare(password, user.password);

      if (match) {
        const token = jwt.sign({ user }, config.TOKEN_KEY, { expiresIn: '24h' });
        res.json({user, token});
      } else {
        // Passwords don't match
        res.status(401).json({error: 'Invalid credentials'});
      }
    } else {
      // User not found
      res.status(404).json({error: 'User not found'});
    }
  } catch (error) {
    console.error('Error logging in:', error);
    res.status(500).json({error: 'Failed to log in'});
  }
};

// Controller function to get user by ID
const getUserById = async (req, res) => {
  try {
    const {id} = req.params;
    console.log(id);
    const query = {
      text: 'SELECT userid,username,email,gender FROM users WHERE UserID = $1',
      values: [id],
    };

    const result = await pool.query(query);

    if (result.rows.length === 0) {
      res.status(404).json({message: 'User not found'});
    } else {
      res.json(result.rows[0]);
    }
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

// Controller function to update user by ID
const updateUserById = async (req, res) => {
  try {
    const {id} = req.params;
    const {username, email, password, gender} = req.body;

    const hashedPassword = await bcrypt.hash(password, 10);

    const query = {
      text: 'UPDATE users SET username = $1, email = $2, password = $3, gender = $4 WHERE UserID = $5 RETURNING *',
      values: [username, email, hashedPassword, gender, id],
    };

    const result = await pool.query(query);

    if (result.rows.length === 0) {
      res.status(404).json({message: 'User not found'});
    } else {
      res.json(result.rows[0]);
    }
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

// Controller function to delete user by ID
const deleteUserById = async (req, res) => {
  try {
    const {id} = req.params;
    const query = {
      text: 'DELETE FROM users WHERE UserID = $1',
      values: [id],
    };

    const result = await pool.query(query);

    if (result.rowCount === 0) {
      res.status(404).json({message: 'User not found'});
    } else {
      res.json({message: 'User deleted successfully'});
    }
  } catch (error) {
    res.status(500).json({error: error.message});
  }
};

module.exports = {
  registerUser,
  loginUser,
  getUserById,
  updateUserById,
  deleteUserById,
};
