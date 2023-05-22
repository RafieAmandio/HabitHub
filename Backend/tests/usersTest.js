const request = require('supertest');
const app = require('../app'); // Assuming your Express app is exported from 'app.js'
const {pool} = require('../config/config');

// Clear the users table before each test
beforeEach(async () => {
  await pool.query('DELETE FROM users');
});

// Test the user registration route
describe('POST /register', () => {
  it('should register a new user', async () => {
    const userData = {
      username: 'testuser',
      email: 'test@example.com',
      password: 'password',
      gender: 'male',
    };

    const response = await request(app)
        .post('/register')
        .send(userData)
        .expect(201);

    expect(response.body).toHaveProperty('username', 'testuser');
    expect(response.body).toHaveProperty('email', 'test@example.com');
    expect(response.body).toHaveProperty('gender', 'male');
    expect(response.body).not.toHaveProperty('password');
  });

  it('should return an error if email is already registered', async () => {
    // Insert a user with the same email before the test
    await pool.query('INSERT INTO users (username, email, password, gender) VALUES ($1, $2, $3, $4)', [
      'existinguser',
      'test@example.com',
      'password',
      'male',
    ]);

    const userData = {
      username: 'testuser',
      email: 'test@example.com',
      password: 'password',
      gender: 'male',
    };

    const response = await request(app)
        .post('/register')
        .send(userData)
        .expect(400);

    expect(response.body).toHaveProperty('error', 'Email is already registered');
  });
});

// Test the user login route
describe('POST /login', () => {
  it('should log in a user with valid credentials', async () => {
    // Insert a user with known credentials before the test
    const hashedPassword = await bcrypt.hash('password', 10);
    await pool.query('INSERT INTO users (username, email, password, gender) VALUES ($1, $2, $3, $4)', [
      'testuser',
      'test@example.com',
      hashedPassword,
      'male',
    ]);

    const loginData = {
      email: 'test@example.com',
      password: 'password',
    };

    const response = await request(app)
        .post('/login')
        .send(loginData)
        .expect(200);

    expect(response.body).toHaveProperty('username', 'testuser');
    expect(response.body).toHaveProperty('email', 'test@example.com');
    expect(response.body).toHaveProperty('gender', 'male');
    expect(response.body).not.toHaveProperty('password');
  });

  it('should return an error with invalid credentials', async () => {
    const loginData = {
      email: 'test@example.com',
      password: 'invalidpassword',
    };

    const response = await request(app)
        .post('/login')
        .send(loginData)
        .expect(401);

    expect(response.body).toHaveProperty('error', 'Invalid credentials');
  });
});

// ... Add more tests for other routes ...

// Clean up the users table after all tests are done
afterAll(async () => {
  await pool.query('DELETE FROM users');
});
