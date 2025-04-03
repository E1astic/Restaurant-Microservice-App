create table if not exists payment(
    id bigint primary key references waiter_order(id),
    payment_type varchar,
    payment_date timestamp with time zone,
    payment_sum numeric
);

comment on table payment is 'Таблица с информацией о платеже для каждого заказа';
comment on column payment.id is 'Уникальный идентификатор платежа, являющийся внешним ключом на id заказа. То есть идентификаторы заказа и платежа совпадают';
comment on column payment.payment_type is 'Тип платежа';
comment on column payment.payment_date is 'Дата платежа';
comment on column payment.payment_sum is 'Сумма платежа';