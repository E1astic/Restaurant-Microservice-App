create table if not exists order_to_dish(
    kitchen_order_id bigint not null references kitchen_order(id) on delete cascade,
    dish_id bigint not null references dish(id) on delete cascade,
    dishes_number bigint not null,
    primary key (kitchen_order_id, dish_id)
);

comment on table order_to_dish is 'Таблица, содержащая список блюд для каждого заказа';
comment on column order_to_dish.kitchen_order_id is 'Внешний ключ на номер заказа (является частью составного первичного ключа)';
comment on column order_to_dish.dish_id is 'Внешний ключ на блюдо (является частью составного первичного ключа)';
comment on column order_to_dish.dishes_number is 'Количество блюд в заказе';