create table if not exists dish(
    id bigserial primary key,
    balance bigint not null,
    short_name varchar not null,
    dish_composition varchar not null
);