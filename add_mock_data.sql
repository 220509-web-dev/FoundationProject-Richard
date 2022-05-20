set search_path to foundation_app;

insert into user_access (name) values ('BASIC'), ('PREMIUM'), ('ADMIN');

insert into users (fname, lname, email, uname, pword, role_id) values ('Zumi', 'Daxuya', 'zumid@gmail.com', 'zumid', 'catboy', 1);
insert into users (fname, lname, email, uname, pword, role_id) values ('Richard', 'Moch', 'richard@richardmoch.xyz', 'soulcatcher', 'testing', 2);

insert into notes (name, note_body, owner_id, visibility) values ('my super secret', 'meow', 1, 0);
insert into notes (name, note_body, owner_id, visibility) values ('super super secret', 'no one can see this :D', 2, 1);