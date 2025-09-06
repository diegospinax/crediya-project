create database webflux_auth;


create table auth(
                     id bigserial,
                     email text unique not null,
                     password text not null,
                     failed_login_attempts integer not null default 0,
                     is_locked boolean default false,
                     role varchar(50) not null,
                     user_id bigint null,

                     primary key (id)
);