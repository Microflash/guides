import winston from 'winston';
import DailyRotateFile from 'winston-daily-rotate-file';
import { environment } from '../configuration/environment';

export const logger = winston.createLogger({
  level: environment.logLevel,
  format: winston.format.simple(),
  transports: [
    new winston.transports.Console(),
    new DailyRotateFile({
      filename: environment.logFile,
      dirname: environment.logDir,
      datePattern: 'YYYY-MM-DD',
      zippedArchive: true,
      maxFiles: '14d',
      maxSize: '20m'
    })
  ]
});
