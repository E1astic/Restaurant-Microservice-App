create table if not exists waiter_order(
    id bigserial primary key,
    status varchar not null,
    create_dttm timestamp with time zone not null,
    waiter_id bigint not null references waiter_account(id) on delete cascade ,
    table_no varchar not null
);

comment on table waiter_order is 'Таблица с информацией о заказах официантов';
comment on column waiter_order.status is 'Статус заказа';
comment on column waiter_order.create_dttm is 'Дата создания заказа';
comment on column waiter_order.waiter_id is 'Id официанта, который принял заказ';
comment on column waiter_order.table_no is 'Номер столика, который обслуживал официант';