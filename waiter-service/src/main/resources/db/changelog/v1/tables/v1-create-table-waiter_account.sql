create table if not exists waiter_account(
    id bigserial primary key,
    name varchar not null,
    employment_date timestamp with time zone not null,
    sex varchar not null
);

comment on table waiter_account is 'Таблица с информацией о профилях официантов';
comment on column waiter_account.name is 'Имя официанта';
comment on column waiter_account.employment_date is 'Дата трудоустройства';
comment on column waiter_account.sex is 'Пол официанта';