set search_path to foundation_app;

select u.uname as author,
n.name as title,
n.note_body,
n.visibility 
from notes n
join users u on u.id = n.owner_id ; 



update users 
set role_id = 3 where id=2;

select *,
ua.name from users u 
join user_access ua 
on ua.id = u.role_id 
order by u.id;