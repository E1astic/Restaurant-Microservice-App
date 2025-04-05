create table if not exists menu(
    id bigserial primary key,
    dish_name varchar not null,
    dish_cost numeric not null
);

comment on table menu is 'Таблица с блюдами из меню';
comment on column menu.dish_name is 'Наименование блюда';
comment on column menu.dish_cost is 'Стоимость блюда';