create extension if not exists "uuid-ossp";
drop table if exists notes;
create table notes (
	id         uuid primary key      default uuid_generate_v1(),
	title      varchar(255) not null,
	body       text,
	created_by varchar(255) not null,
	created_at timestamptz  not null default localtimestamp,
	updated_by varchar(255) not null,
	updated_at timestamptz  not null default localtimestamp,
	version    smallint     not null default 1,
	word_count int          not null,
	read_only  boolean      not null default false
);
