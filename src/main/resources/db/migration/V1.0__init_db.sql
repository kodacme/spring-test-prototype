
drop table if exists fruits cascade;
drop table if exists kinds cascade;
drop table if exists producers cascade;
drop table if exists users cascade;

create table fruits (
  id varchar(10),
  name varchar(30),
  kind varchar(10)
);

create table kinds (
  code varchar(10),
  name varchar(30)
);

create table producers (
  id varchar(10),
  address varchar(120),
  lastname varchar(60),
  firstname varchar(60)
);

create table users (
  id bigserial not null,
  username varchar(60),
  password varchar(100),
  roles varchar(100)
);
