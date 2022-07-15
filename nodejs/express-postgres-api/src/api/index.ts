import { Application } from 'express';
import { registerDefaultEndpoints } from './default';
import { registerSongEndpoints } from './song';

export const registerEndpoints = (app: Application) => {
  registerDefaultEndpoints(app);
  registerSongEndpoints(app);
};
