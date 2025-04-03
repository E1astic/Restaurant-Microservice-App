create table if not exists order_position(
    id bigserial primary key,
    dish_num bigint not null,
    order_id bigint not null references waiter_order(id),
    menu_position_id bigint not null references menu(id)
);