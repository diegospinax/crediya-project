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

insert into roles (name, description) VALUES ('ADMIN', 'Admin role.');
insert into roles (name, description) VALUES ('CONSULTANT', 'Consultant role.');
insert into roles (name, description) VALUES ('ADVISOR', 'Advisor role.');

insert into "users" (name, lastname, document, date_birth, address, phone_number, email, salary, id_role)
values ('DIEGO', 'SÁNCHEZ', '1032700000', '2004-03-11', 'CALLE_21_#74-10._BOGOTÁ,_COLOMBIA', '3153900000', 'd@mail.com', 5500000, 1)