create table if not exists waiter_account(
    id bigserial primary key,
    name varchar not null,
    employment_date timestamp with time zone not null,
    sex varchar not null
);