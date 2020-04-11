CREATE TABLE custom_user (
	id SERIAL PRIMARY KEY,
	email TEXT,
	name TEXT,
	password TEXT
);

CREATE TABLE note (
	id SERIAL PRIMARY KEY,
	title TEXT,
	content TEXT,
	last_update TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);