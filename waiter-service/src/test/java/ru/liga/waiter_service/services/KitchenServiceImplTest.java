package ru.liga.waiter_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.producer.WaiterOrderProducer;
import ru.liga.waiter_service.services.kitchen.KitchenServiceImpl;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KitchenServiceImplTest {

    @Mock
    private WaiterOrderProducer waiterOrderProducer;

    @Mock
    private WaiterOrderConverter waiterOrderConverter;

    @InjectMocks
    private KitchenServiceImpl kitchenService;

    private WaiterOrder waiterOrder;
    private WaiterOrderRequest waiterOrderRequest;
    private KitchenOrderRequest kitchenOrderRequest;

    @BeforeEach
    void init() {
        waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, null, null);
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);
        kitchenOrderRequest = new KitchenOrderRequest(1L, orderPositions);
    }

    @Test
    void shouldSendOrderToKitchen() {
        // Подготовка
        when(waiterOrderConverter.mapToKitchenOrderRequest(waiterOrderRequest, waiterOrder.getId()))
                .thenReturn(kitchenOrderRequest);
        doNothing().when(waiterOrderProducer).produce(kitchenOrderRequest);

        // Действие
        kitchenService.sendOrderToKitchen(waiterOrderRequest, waiterOrder);

        // Проверка
        verify(waiterOrderProducer, times(1)).produce(kitchenOrderRequest);
        verify(waiterOrderConverter, times(1))
                .mapToKitchenOrderRequest(waiterOrderRequest, waiterOrder.getId());
    }
}
