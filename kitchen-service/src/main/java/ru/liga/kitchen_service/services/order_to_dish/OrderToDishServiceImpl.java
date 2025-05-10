package ru.liga.kitchen_service.services.order_to_dish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.mappers.OrderToDishMapper;
import ru.liga.kitchen_service.models.entity.KitchenOrder;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderToDishServiceImpl implements OrderToDishService {

    private final OrderToDishMapper orderToDishMapper;

    @Override
    public void addOrderToDishes(KitchenOrder kitchenOrder, KitchenOrderRequest kitchenOrderRequest) {
        log.info("Добавление в базу данных кухни всех позиций заказа с id = {}", kitchenOrder.getId());
        orderToDishMapper.saveAll(kitchenOrder.getId(),
                kitchenOrderRequest.getOrderPositions());
    }
}
