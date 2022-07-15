import express from 'express';
import { registerRequestLogger } from './helper/request.logger';
import { logger } from './helper/default.logger';

const app = express();
const port = 8080; // server port

// register morgan
registerRequestLogger(app);

// default route
app.get('/', (req, res) => res.json({
  message: 'Hello World!'
}));

// server
app.listen(port, () => logger.info(`Server started at http://localhost:${port}`));
