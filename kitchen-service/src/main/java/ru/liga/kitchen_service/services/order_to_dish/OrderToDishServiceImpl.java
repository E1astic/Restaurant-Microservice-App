package ru.liga.kitchen_service.services.order_to_dish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.mappers.OrderToDishMapper;
import ru.liga.kitchen_service.models.entity.KitchenOrder;


@Service
@RequiredArgsConstructor
public class OrderToDishServiceImpl implements OrderToDishService {

    private final OrderToDishMapper orderToDishMapper;

    public void addOrderToDishes(KitchenOrder kitchenOrder,
                                 KitchenOrderRequest kitchenOrderRequest) {
        orderToDishMapper.saveAll(kitchenOrder.getId(),
                kitchenOrderRequest.getOrderPositions());
    }
}
