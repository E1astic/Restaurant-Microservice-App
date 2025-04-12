package ru.liga.waiter_service.services.order_position;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;

public interface OrderPositionService {

    void addOrderPositions(WaiterOrder waiterOrder, WaiterOrderRequest waiterOrderRequest);

}
