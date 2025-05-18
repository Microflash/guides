create table book (
	id     uuid default random_uuid() primary key,
	title  varchar(255) not null,
	author varchar(255) not null,
	genre  varchar(255) array not null
);

insert into book (title, author, genre)
values ('Royal Gambit', 'Daniel O''Malley', array['Fantasy', 'Science Fiction']),
			 ('Nemesis', 'Gregg Hurwitz', array['Action', 'Thriller']),
			 ('Shroud', 'Adrian Tchaikovsky', array['Science Fiction', 'Horror']),
			 ('Careless People', 'Sarah Wynn-Williams', array['Memoir']),
			 ('Raising Hare', 'Chloe Dalton', array['Memoir']),
			 ('System Collapse', 'Martha Wells', array['Science Fiction', 'Space Opera']);
