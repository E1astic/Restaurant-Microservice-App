create table if not exists dish(
    id bigserial primary key,
    balance bigint not null,
    short_name varchar not null,
    dish_composition varchar not null
);

comment on table dish is 'Таблица с блюдами';
comment on column dish.balance is 'Стоимость блюда';
comment on column dish.short_name is 'Наименование блюда';
comment on column dish.balance is 'Состав, ингридиенты блюда';