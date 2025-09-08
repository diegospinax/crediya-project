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

insert into auth (email, password, role, user_id) VALUES ('d@mail.com', '$2y$10$xDUukG8ecrGh30HEy2bfQ.2h4XeqbrmnGYRCCrV/e59F0UOxMJ5h2', 'ADMIN', 1);