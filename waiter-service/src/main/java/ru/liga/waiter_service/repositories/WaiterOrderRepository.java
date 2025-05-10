package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.models.enums.OrderStatus;

/**
 * Репозиторий для работы с данными о заказах официантов.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций CRUD сущности {@link WaiterOrder}
 * через Spring Data JPA. Наследуется от {@link JpaRepository}, что обеспечивает базовые операции
 * для управления заказами официантов. Также включает дополнительный метод для обновления статуса заказа.
 * </p>
 */
@Repository
public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {

    /**
     * Обновляет статус заказа по его идентификатору.
     * <p>
     * Метод выполняет SQL-запрос на обновление поля {@code status} в таблице "waiter_order".
     * Если заказ с указанным ID существует, его статус изменяется на новый. Возвращает количество
     * обновленных записей (обычно 1, если запись найдена).
     * </p>
     *
     * @param id     уникальный идентификатор заказа, который необходимо обновить. Не должен быть {@code null}.
     * @param status новый статус заказа ({@link OrderStatus}). Не должен быть {@code null}.
     * @return количество обновленных записей в базе данных (обычно 1, если запись найдена).
     */
    @Modifying
    @Query("UPDATE WaiterOrder wo SET wo.status = :status WHERE wo.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);
}
