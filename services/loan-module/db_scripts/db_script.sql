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

insert into loan_types (name, min_amount, max_amount, interest_rate, automatic_validate)
values ('MORTGAGE', 100, 200000, 0.2, false);

insert into states (name, description) values
-- 1. Start of the process
('RECEIVED', 'The loan application has been received but has not yet been reviewed.'),

-- 2. Review phase
('UNDER_REVIEW', 'The application is currently being reviewed by a credit analyst.'),

-- 3. Outcome
('PRE_APPROVED', 'The application has been pre-approved, subject to further validations.'),
('APPROVED', 'The application has been approved and is ready for disbursement.'),
('REJECTED', 'The application has been rejected for not meeting the criteria.'),

-- 4. Disbursement and closure
('DISBURSED', 'The loan has been successfully disbursed.'),
('CANCELLED_BY_CLIENT', 'The client has requested to cancel the application process.'),
('CANCELLED_BY_BANK', 'The bank has cancelled the application due to inactivity, inconsistencies, or other reasons.'),

-- 5. Follow-up
('UNDER_LEGAL_REVIEW', 'The application is under legal review before disbursement.'),
('IN_CONTRACT_SIGNING', 'The client is in the process of signing the loan agreement.');