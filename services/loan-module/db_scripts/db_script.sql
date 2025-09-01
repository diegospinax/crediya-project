create database webflux_loan;

create table loan_types (
    id BIGSERIAL,
    name varchar(50) NOT NULL,
    min_amount int NOT NULL,
    max_amount int NOT NULL,
    interest_rate numeric(5,4) not null check(interest_rate >= 0 AND interest_rate <= 1),
    automatic_validate boolean not null,

    primary key (id)
);

create table states (
    id bigserial,
    name varchar(100) NOT NULL UNIQUE,
    description text NOT NULL,

    primary key (id)
);

create table loans (
    id bigserial,
    amount int not null check (amount > 0),
    term date NOT NULL check(term > current_date),
    document varchar(10) NOT NULL,
    state_id bigint not null,
    loan_type_id bigint not null,

    primary key (id),

    constraint fk_loan_request_state foreign key (state_id) references states(id),
    constraint fk_loan_request_loan_type foreign key (loan_type_id) references loan_types(id)
);