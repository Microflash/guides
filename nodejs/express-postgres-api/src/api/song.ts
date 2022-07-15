import { Application } from 'express';
import { SongRepository } from '../repository/song';

const context = '/song';
const repository = new SongRepository();

export const registerSongEndpoints = (app: Application) => {
  app.get(context, (req, res) => {
    repository.getAll()
      .then(results => res.json({ results: results }))
      .catch(error => res.status(500).json({ error: error }));
  });

  app.get(`${context}/search`, (req, res) => {
    if (req.query.q) {
      repository.search(req.query.q)
        .then(results => res.json({ results: results }))
        .catch(error => res.status(500).json({ error: error }));
    } else {
      res.status(400).json({ error: 'No query found' });
    }
  });

  app.put(context, (req, res) => {
    if (req.body && req.body.title && req.body.album && req.body.artist) {
      const song = {
        title: req.body.title,
        album: req.body.album,
        artist: req.body.artist
      };
      repository.save(song)
        .then(result => res.json({ message: `Saved ${song.title}` }))
        .catch(error => res.status(500).json({ error: error }));
    } else {
      res.status(400).json({ error: 'Invalid request body' });
    }
  });

  app.patch(context, (req, res) => {
    if (req.body) {
      if (req.body.id && (req.body.title || req.body.album || req.body.artist)) {
        repository.getById(req.body.id)
          .then(result => {
            const patchedSong = {
              id: result[0].id,
              title: req.body.title ? req.body.title : result[0].title,
              album: req.body.album ? req.body.album : result[0].album,
              artist: req.body.artist ? req.body.artist : result[0].artist
            };
            repository.save(patchedSong)
              .then(result => res.json({ message: `Updated ${patchedSong.title}` }))
              .catch(error => res.status(500).json({ error: error }));
          })
          .catch(error => res.status(500).json({ error: error }));
      } else {
        res.status(400).json({ error: 'Invalid request body' });
      }
    } else {
      res.status(400).json({ error: 'Empty request body' });
    }
  });

  app.delete(context, (req, res) => {
    if (req.body && req.body.id) {
      repository.remove(req.body.id)
        .then(result => res.json({ message: `Song with id ${req.body.id} deleted` }))
        .catch(error => res.status(500).json({ error: error }));
    } else {
      res.status(400).json({ error: 'Empty request body' });
    }
  });
};
