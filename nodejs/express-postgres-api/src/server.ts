import express from 'express';
import bodyParser from 'body-parser';
import { environment } from './configuration/environment';
import { registerRequestLogger } from './helper/request.logger';
import { logger } from './helper/default.logger';
import { registerEndpoints } from './api';

const app = express();
const port = environment.serverPort; // server port
app.use(bodyParser.json()); // body parser to read request body

// register morgan
registerRequestLogger(app);

// register all endpoints
registerEndpoints(app);

// server
app.listen(port, () => logger.info(`Server started at http://localhost:${port}, Press CTRL+C to stop it`));
