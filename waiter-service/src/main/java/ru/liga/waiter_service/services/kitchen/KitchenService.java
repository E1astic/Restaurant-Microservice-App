package ru.liga.waiter_service.services.kitchen;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;

public interface KitchenService {

    void sendOrderToKitchen(WaiterOrderRequest waiterOrderRequest, WaiterOrder waiterOrder);
}
