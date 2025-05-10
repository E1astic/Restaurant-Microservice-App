package ru.liga.waiter_service.services.order_position;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;

/**
 * Интерфейс для работы с позициями заказов.
 * <p>
 * Предоставляет методы для добавления позиций в заказ. Используется для обработки данных о блюдах,
 * добавленных в заказ официантом, и их сохранения в системе.
 * </p>
 */
public interface OrderPositionService {

    /**
     * Добавляет позиции в заказ.
     * <p>
     * Метод обрабатывает данные о позициях из запроса ({@link WaiterOrderRequest}) и связывает их с указанным заказом
     * ({@link WaiterOrder}). Используется для создания записей о составе заказа в базе данных.
     * </p>
     *
     * @param waiterOrder        объект заказа, к которому добавляются позиции. Не должен быть {@code null}.
     * @param waiterOrderRequest объект запроса, содержащий информацию о позициях заказа. Не должен быть {@code null}.
     */
    void addOrderPositions(WaiterOrder waiterOrder, WaiterOrderRequest waiterOrderRequest);
}
