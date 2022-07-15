import { Application } from 'express';

export const registerDefaultEndpoints = (app: Application) => {
  app.get('/health', (req, res) => res.json({
    status: 'UP'
  }));
};
