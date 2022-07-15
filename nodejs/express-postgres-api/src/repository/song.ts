import { Pool } from 'pg';
import { Song } from '../model/song';
import { logger } from '../helper/default.logger';

export class SongRepository {
  private pool: Pool;

  constructor() {
    this.pool = new Pool();
  }

  async getAll() {
    logger.info('Fetching all songs...');
    const result = await this.pool.query('SELECT id, title, album, artist FROM song');
    return result.rows;
  }

  async getById(id: string) {
    logger.info(`Fetching the song with id ${id}...`);
    const result = await this.pool.query(`SELECT id, title, album, artist FROM song WHERE id = '${id}'`);
    return result.rows;
  }

  async search(keyword: string) {
    logger.info(`Searching for ${keyword}...`);
    const result = await this.pool.query(`SELECT id, title, album, artist FROM song WHERE title LIKE '%${keyword}%' OR album LIKE '%${keyword}%' OR artist LIKE '%${keyword}%'`);
    return result.rows;
  }

  async save(song: Song) {
    const client = await this.pool.connect();
    try {
      const result = await client.query(
        `INSERT INTO song(title, album, artist) VALUES ('${song.title}', '${song.album}', '${song.artist}') ON CONFLICT (id) DO UPDATE SET title = '${song.title}', album = '${song.album}', artist = '${song.artist}'`
      );
      await client.query('COMMIT');
      logger.info('Song saved...');
      return result.rows;
    } catch (e) {
      await client.query('ROLLBACK');
      logger.info('Failed saving the song. Transaction rolled back...');
      throw e;
    } finally {
      client.release();
    }
  }

  async remove(id: string) {
    logger.info('Deleting a song...');
    const result = await this.pool.query(`DELETE FROM song WHERE id = '${id}'`);
    return result.rows;
  }
}
