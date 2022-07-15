import dotenv from 'dotenv';

// fetch .env configuration
dotenv.config();

export const environment = {
  nodeEnv: process.env.ENV || process.env.NODE_ENV,
  logDir: process.env.LOG_DIR || 'logs',
  logLevel: process.env.LOG_LEVEL || 'info',
  logFile: process.env.LOG_FILE || 'app.log',
  pgUser: process.env.PGUSER,
  pgHost: process.env.PGHOST,
  pgPassword: process.env.PGPASSWORD,
  pgDatabase: process.env.PGDATABASE,
  pgPort: Number(process.env.PGPORT),
  requestLogFile: process.env.MORGAN_LOG || 'requests.log',
  requestLogFormat: process.env.MORGAN_LOG_FMT || 'combined',
  requestLogRollingInterval: process.env.MORGAN_LOG_ROLLING_INTERVAL || '1d',
  serverPort: Number(process.env.SERVER_PORT) || 8080
};