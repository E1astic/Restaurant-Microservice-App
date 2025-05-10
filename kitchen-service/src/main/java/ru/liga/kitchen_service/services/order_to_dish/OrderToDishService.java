package ru.liga.kitchen_service.services.order_to_dish;

import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.entity.KitchenOrder;

/**
 * Интерфейс для работы с данными о связях между заказами и блюдами.
 * <p>
 * Предоставляет методы для выполнения операций, связанных с добавлением связей между заказами кухни
 * и блюдами. Используется для взаимодействия с сервисами или мапперами, которые сохраняют эти связи
 * в базе данных.
 * </p>
 */
@Service
public interface OrderToDishService {

    /**
     * Добавляет связи между заказом кухни и блюдами.
     * <p>
     * Метод обрабатывает данные из объектов {@link KitchenOrder} и {@link KitchenOrderRequest},
     * создает записи о связях между заказом и блюдами и сохраняет их
     * в базе данных. Используется для детализации состава заказа.
     * </p>
     *
     * @param kitchenOrder объект заказа кухни, для которого добавляются связи. Не должен быть {@code null}.
     * @param kitchenOrderRequest объект запроса, содержащий информацию о блюдах в заказе. Не должен быть {@code null}.
     */
    void addOrderToDishes(KitchenOrder kitchenOrder, KitchenOrderRequest kitchenOrderRequest);
}
