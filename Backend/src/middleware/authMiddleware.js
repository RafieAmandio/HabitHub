const jwt = require('jsonwebtoken');

// Authentication middleware function
const authenticate = (req, res, next) => {
  // Check if the request contains a token in the headers or query string
  const token = req.headers.authorization || req.query.token;

  // Verify and decode the token
  try {
    const decoded = jwt.verify(token, 'denise'); // Replace 'your-secret-key' with your actual secret key
    req.user = decoded;
    next();
  } catch (error) {
    return res.status(401).json({error: 'Authentication failed'});
  }
};

const generateToken = (email) => {
  const secretKey = 'denise'; // Replace 'your-secret-key' with your actual secret key
  const token = jwt.sign({email}, secretKey, {expiresIn: '1h'});
  return token;
};

module.exports = {
  authenticate,
  generateToken,
};
