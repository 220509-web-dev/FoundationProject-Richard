create table user_data (
	id SERIAL,
	uname VARCHAR,
	pass VARCHAR
);

insert into user_data
values  (1, 'soulcatcher', 'securepass');

insert into user_data (uname, pass)
values ('zumid', 'kittycat');

select * from user_data;
