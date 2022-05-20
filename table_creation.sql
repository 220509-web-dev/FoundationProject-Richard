create schema foundation_app;
set search_path to foundation_app;

create table user_access (
    id      int generated always as identity primary key,
    name    varchar unique not null
);

create table users (
    
);

create table notes (

);