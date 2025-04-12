package ru.liga.waiter_service.services.waiter_order;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.List;

public interface WaiterOrderService {

    List<WaiterOrderResponse> getAllOrders();

    WaiterOrderResponse getOrderById(Long id);

    OrderStatus getOrderStatusById(Long id);

    WaiterOrder addOrder(WaiterOrderRequest waiterOrderRequest);

    void setStatus(Long id, String status);
}
