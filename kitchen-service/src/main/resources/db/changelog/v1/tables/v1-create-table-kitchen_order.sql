create table if not exists kitchen_order(
    id bigserial primary key,
    waiter_order_no bigint not null,
    status varchar not null,
    create_dttm timestamp with time zone not null
);

comment on table kitchen_order is 'Таблица с заказами кухни';
comment on column kitchen_order.waiter_order_no is 'Номер заказа официанта';
comment on column kitchen_order.status is 'Статус готовности заказа';
comment on column kitchen_order.create_dttm is 'Дата создания заказа';