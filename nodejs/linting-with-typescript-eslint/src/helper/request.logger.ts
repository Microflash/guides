import { Application } from 'express';
import morgan from 'morgan';
import { createStream } from 'rotating-file-stream';
import { environment } from '../configuration/environment';

// rotating stream for morgan
const accessLogStream = createStream(environment.requestLogFile, {
  interval: environment.requestLogRollingInterval,
  path: environment.logDir
});

// appenders for printing the logs to console and file
const consoleAppender = morgan(environment.requestLogFormat);
const fileAppender = morgan(environment.requestLogFormat, {
  stream: accessLogStream
});

// function to inject morgan in an express app
export const registerRequestLogger = (app: Application) => {
  app.use(consoleAppender);

  // log to file only in `production`
  if (environment.nodeEnv === 'production') {
    app.use(fileAppender);
  }
};