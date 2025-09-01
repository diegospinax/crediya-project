create database webflux_user;

create table "roles"
(
    id          bigserial,
    name        varchar(50) unique not null,
    description text               not null,

    primary key (id)
);

CREATE TABLE "users"
(
    id           BIGSERIAL,
    name         varchar(255)   NOT NULL,
    lastname     varchar(255)   NOT NULL,
    document     varchar(10)    NOT NULL UNIQUE CHECK (document ~ '^\d{10}$'),
    date_birth   date           NOT NULL,
    address      text           NOT NULL,
    phone_number varchar(10)    NOT NULL CHECK (phone_number ~ '^[0-9]{10}$'),
    email        text           NOT NULL UNIQUE,
    salary       numeric(10, 2) NOT NULL CHECK ( salary > 0.00 AND salary < 15000000.00),
    id_role      bigint         not null,

    primary key (id),
    constraint fk_users_role foreign key (id_role) references roles (id)
);

create index idx_users_id_role on users (id_role);