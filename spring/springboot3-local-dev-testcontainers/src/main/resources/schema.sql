create extension if not exists "uuid-ossp";

create table notes (
	id        uuid primary key      default uuid_generate_v1(),
	title     varchar(255) not null,
	body      text,
	read_only boolean      not null default false
);
