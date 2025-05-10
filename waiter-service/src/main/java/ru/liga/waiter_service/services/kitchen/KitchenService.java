package ru.liga.waiter_service.services.kitchen;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;

/**
 * Интерфейс для взаимодействия с сервисом кухни.
 * <p>
 * Предоставляет методы для отправки заказов официантов на кухню. Используется для обработки заказов,
 * созданных официантами, и передачи их в систему кухни для дальнейшего выполнения.
 * </p>
 */
public interface KitchenService {

    /**
     * Отправляет заказ официанта на кухню.
     * <p>
     * Метод выполняет отправку данных о заказе ({@link WaiterOrderRequest}) и связанной сущности
     * {@link WaiterOrder} в систему кухни для дальнейшей обработки.
     * </p>
     *
     * @param waiterOrderRequest объект запроса, содержащий информацию о заказе официанта.
     *                           Не должен быть {@code null}.
     * @param waiterOrder        объект заказа, связанный с данным запросом.
     *                           Не должен быть {@code null}.
     */
    void sendOrderToKitchen(WaiterOrderRequest waiterOrderRequest, WaiterOrder waiterOrder);
}
