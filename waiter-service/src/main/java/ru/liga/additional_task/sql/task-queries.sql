explain analyse select * from waiter_account where name='Иванов Иван';
explain analyse select * from waiter_account where sex='Male';

SELECT
    i.relname AS "Таблица",
    indexrelname AS "Индекс",
    pg_size_pretty(pg_total_relation_size(i.relid)) AS "Размер таблицы с индексами",
    pg_size_pretty(pg_indexes_size(i.relid)) AS "Размер всех индексов",
    pg_size_pretty(pg_relation_size(c.oid)) AS "Размер индекса"
FROM
    pg_stat_all_indexes i
        JOIN
    pg_class c ON i.indexrelid = c.oid
WHERE
    i.relname = 'waiter_account';