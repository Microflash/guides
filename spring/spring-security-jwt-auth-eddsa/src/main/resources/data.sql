create table if not exists custom_user (
	id uuid default random_uuid() primary key,
	username varchar(50) not null unique,
	password varchar(500) not null
);
