create table book (id uuid default random_uuid() primary key, title varchar(255) not null, genre varchar(255) not null, author varchar(255) not null);

insert into book (title, genre, author) values ('Dark Matter', 'SCI_FI', 'Blake Crouch');
