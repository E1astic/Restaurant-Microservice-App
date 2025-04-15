package ru.liga.kitchen_service.services.kitchen_service;

import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;

import java.util.List;


public interface KitchenService {

    List<KitchenOrderResponse> getAllOrders();

    List<KitchenOrderWithDishesResponse> getAllOrdersWithDishes();

    KitchenOrder acceptOrder(KitchenOrderRequest kitchenOrderRequest);

    void setStatus(Long id, String status);

    Long receiveOrder(KitchenOrderRequest kitchenOrderRequest);
}
