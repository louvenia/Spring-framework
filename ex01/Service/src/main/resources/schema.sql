drop table if exists users cascade;

create table if not exists users (
    id bigserial primary key,
    email varchar(50) not null
);