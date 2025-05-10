package ru.liga.kitchen_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.mappers.OrderToDishMapper;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishServiceImpl;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderToDishServiceImplTest {

    @Mock
    private OrderToDishMapper orderToDishMapper;

    @InjectMocks
    private OrderToDishServiceImpl orderToDishService;

    @Test
    void shouldAddOrderToDishes() {
        // Подготовка
        KitchenOrder kitchenOrder = new KitchenOrder(1L, 1L,
                KitchenStatus.PREPARING, OffsetDateTime.now());
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        KitchenOrderRequest kitchenOrderRequest = new KitchenOrderRequest(1L, orderPositions);

        doNothing().when(orderToDishMapper).saveAll(kitchenOrder.getId(), kitchenOrderRequest.getOrderPositions());

        // Действие
        orderToDishService.addOrderToDishes(kitchenOrder, kitchenOrderRequest);

        // Выполнение
        verify(orderToDishMapper, times(1)).saveAll(
                kitchenOrder.getId(), kitchenOrderRequest.getOrderPositions());
    }
}
