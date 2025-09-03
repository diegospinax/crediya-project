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
values ('HIPOTECARIO', 100, 200000, 0.2, false);

insert into states (name, description) values
-- 1. Start of the process
('Received', 'The loan application has been received but has not yet been reviewed.'),

-- 2. Review phase
('Under Review', 'The application is currently being reviewed by a credit analyst.'),
('Pending Documentation', 'Additional documentation has been requested from the client.'),
('Incomplete Documentation', 'The submitted documentation is incomplete or does not meet requirements.'),
('Under Risk Evaluation', 'The application is being evaluated by the risk assessment team.'),

-- 3. Outcome
('Pre-Approved', 'The application has been pre-approved, subject to further validations.'),
('Approved', 'The application has been approved and is ready for disbursement.'),
('Rejected', 'The application has been rejected for not meeting the criteria.'),

-- 4. Disbursement and closure
('Disbursed', 'The loan has been successfully disbursed.'),
('Cancelled by Client', 'The client has requested to cancel the application process.'),
('Cancelled by Bank', 'The bank has cancelled the application due to inactivity, inconsistencies, or other reasons.'),

-- 5. Follow-up
('Under Legal Review', 'The application is under legal review before disbursement.'),
('In Contract Signing', 'The client is in the process of signing the loan agreement.');