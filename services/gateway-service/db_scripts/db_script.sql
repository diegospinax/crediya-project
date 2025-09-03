create database webflux_auth;


create table auth(
                     id bigserial,
                     email text unique not null,
                     password text not null,
                     failedLoginAttempts integer not null default 0,
                     isLocked boolean default false,
                     role varchar(50) not null,
                     user_id bigint null,

                     primary key (id)
);