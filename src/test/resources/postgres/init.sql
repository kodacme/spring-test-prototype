
create table fruits (
  id varchar(10),
  name varchar(30),
  kind varchar(10)
);

create table kinds (
  code varchar(10),
  name varchar(30)
);

insert into
  kinds (code, name)
values ('K01', 'Cucurbitaceae'), ('K02', 'Rutaceae');
