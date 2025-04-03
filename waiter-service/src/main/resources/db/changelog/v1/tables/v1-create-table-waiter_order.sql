create table if not exists waiter_order(
    id bigserial primary key,
    status varchar not null,
    create_dttm timestamp with time zone not null,
    waiter_id bigint not null references waiter_account(id),
    table_no varchar not null
);