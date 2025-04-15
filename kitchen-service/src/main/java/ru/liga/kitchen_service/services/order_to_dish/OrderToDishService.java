package ru.liga.kitchen_service.services.order_to_dish;

import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.entity.KitchenOrder;

@Service
public interface OrderToDishService {

     void addOrderToDishes(KitchenOrder kitchenOrder, KitchenOrderRequest kitchenOrderRequest);
}
