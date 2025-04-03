create table if not exists menu(
    id bigserial primary key,
    dish_name varchar not null,
    dish_cost numeric not null
);