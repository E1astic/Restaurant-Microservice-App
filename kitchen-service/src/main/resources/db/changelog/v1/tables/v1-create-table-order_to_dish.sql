create table order_to_dish(
    kitchen_order_id bigint not null references kitchen_order(id),
    dish_id bigint not null references dish(id),
    dishes_number bigint not null,
    primary key (kitchen_order_id, dish_id)
);