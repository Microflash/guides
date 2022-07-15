CREATE TABLE song (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  album TEXT,
  artist TEXT
);

INSERT INTO song(title, album, artist) VALUES ('Too Much', 'Dedicated', 'Carly Rae Jepsen');
INSERT INTO song(title, album, artist) VALUES ('Insomnia', 'D2', 'Daya');
INSERT INTO song(title, album, artist) VALUES ('Issues', 'Nervous System', 'Julia Michaels');
