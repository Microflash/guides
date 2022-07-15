import express from 'express';

const app = express();
const port = 8080; // server port

// default route
app.get('/', (req, res) => res.json({
  message: 'Hello World!'
}));

// server
app.listen(port, () => console.log(`Server started at http://localhost:${port}`));
