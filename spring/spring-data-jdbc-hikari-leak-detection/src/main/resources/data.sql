create table book (id uuid default random_uuid() primary key, title varchar(255) not null, genre varchar(255) not null, author varchar(255) not null);

insert into book (title, genre, author) values ('City of Last Chances', 'SCIENCE_FICTION', 'Adrian Tchaikovsky');
insert into book (title, genre, author) values ('Children of Memory', 'SCIENCE_FICTION', 'Adrian Tchaikovsky');
insert into book (title, genre, author) values ('No Plan B', 'THRILLER', 'Lee Child');
