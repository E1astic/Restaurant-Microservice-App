package ru.liga.kitchen_service.services.order_processing;

import ru.liga.common.dto.KitchenOrderRequest;

/**
 * Интерфейс для обработки заказов кухни.
 * <p>
 * Предоставляет методы для получения заказа кухней с логикой обработки принятия/отклонения заказа.
 * </p>
 */
public interface OrderProcessingService {

    /**
     * Обрабатывает новый заказ, полученный кухней.
     * <p>
     * Метод принимает данные о заказе из объекта {@link KitchenOrderRequest}, выполняет необходимые операции
     * и возвращает уникальный
     * идентификатор созданного заказа.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию о заказе. Не должен быть {@code null}.
     * @return уникальный идентификатор созданного заказа.
     */
    Long processOrder(KitchenOrderRequest kitchenOrderRequest);
}
