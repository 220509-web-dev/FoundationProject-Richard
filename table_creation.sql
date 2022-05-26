create schema foundation_app;
set search_path to foundation_app;

create table user_access (
    id          int generated always as identity primary key,           -- access role id
    name        varchar unique not null                                 -- access role name
);

create table users ( -- make the users
    id          int generated always as identity primary key,
    fname       varchar not null,                                       -- first name
    lname       varchar not null,                                       -- last name
    email       varchar unique not null,                                -- email
    uname       varchar unique not null check (length(uname) > 3),      -- username
    pword       varchar not null check (length(pword) > 3),             -- password
    role_id     int,                                                    -- access role they have

    constraint user_access_fk
    foreign key (role_id)
    references user_access(id)
);

create table notes (
    id          int generated always as identity primary key,
    name        varchar not null,
    note_body   varchar not null,
    owner_id    int,
    visible     boolean,

    constraint owner_id_fk
    foreign key (owner_id)
    references users(id)
);