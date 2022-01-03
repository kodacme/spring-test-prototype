
drop table if exists fruits cascade;
drop table if exists kinds cascade;

create table fruits (
  id varchar(10),
  name varchar(30),
  kind varchar(10)
);

create table kinds (
  code varchar(10),
  name varchar(30)
);
