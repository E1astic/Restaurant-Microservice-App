create table if not exists order_position(
    id bigserial primary key,
    dish_num bigint not null,
    order_id bigint not null references waiter_order(id),
    menu_position_id bigint not null references menu(id)
);

comment on table order_position is 'Таблица, содержащая блюда из меню для каждого заказа';
comment on column order_position.dish_num is 'Количество блюд в заказе';
comment on column order_position.order_id is 'Id заказа (внешний ключ на таблицу с заказами)';
comment on column order_position.menu_position_id is 'Id блюда из меню (внешний ключ на таблицу с блюдами)';