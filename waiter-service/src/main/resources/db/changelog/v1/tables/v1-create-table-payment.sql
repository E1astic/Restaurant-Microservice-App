create table if not exists payment(
    id bigint primary key references waiter_order(id),
    payment_type varchar,
    payment_date timestamp with time zone,
    payment_sum numeric
);