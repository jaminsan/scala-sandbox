create schema if not exists test;

create table test.user(
    id int auto_increment,
    name varchar(20) not null default '',
    age int not null,
    primary key (id)
);