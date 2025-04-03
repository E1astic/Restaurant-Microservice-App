create table if not exists kitchen_order(
    id bigserial primary key,
    waiter_order_no bigint not null,
    status varchar not null,
    create_dttm timestamp with time zone not null
);