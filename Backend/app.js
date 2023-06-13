const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const {testDatabaseConnection} = require('./src/config/config');
const usersRoutes = require('./src/routes/usersRoutes');
const goalsRoutes = require('./src/routes/goalsRoutes');
const habitRoutes = require('./src/routes/habitsRoutes');
// const frequencyRoutes = require('./routes/frequencyRoutes');
// const checklistRoutes = require('./src/routes/checklistRoutes');
// const progressRoutes = require('./routes/progressRoutes');
// const pointRoutes = require('./routes/pointRoutes');

const app = express();

// Middleware
app.use(cors());
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// Routes
app.use('/users', usersRoutes);
app.use('/goals', goalsRoutes);
app.use('/habits', habitRoutes);
// app.use('/frequencies', frequencyRoutes);
// app.use('/checklists', checklistRoutes);
// app.use('/progress', progressRoutes);
// app.use('/points', pointRoutes);

// testDatabaseConnection();
// Start the server

// create /test route to test server
app.get('/test', (req, res) => {
  res.send('Server is working. Please post at "/users" to create a user.');
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

