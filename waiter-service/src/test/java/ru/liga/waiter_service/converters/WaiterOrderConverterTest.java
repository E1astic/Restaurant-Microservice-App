package ru.liga.waiter_service.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class WaiterOrderConverterTest {

    private WaiterOrderConverter waiterOrderConverter;

    @BeforeEach
    void init() {
        waiterOrderConverter = Mappers.getMapper(WaiterOrderConverter.class);
    }

    @Test
    void shouldMapToWaiterOrder() {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);
        WaiterAccount waiterAccount = new WaiterAccount(1L, "Name", null, "MALE", null);
        WaiterOrder expected = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, waiterAccount, null);

        // Действие
        WaiterOrder actual = waiterOrderConverter.mapToWaiterOrder(waiterOrderRequest, waiterAccount);
        actual.setCreateDateTime(expected.getCreateDateTime());
        actual.setId(1L);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldMapToKitchenOrderRequest() {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);
        Long orderId = 1L;
        KitchenOrderRequest expected = new KitchenOrderRequest(orderId, orderPositions);

        // Действие
        KitchenOrderRequest actual = waiterOrderConverter.mapToKitchenOrderRequest(waiterOrderRequest, orderId);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldMapToWaiterOrderResponse() {
        // Подготовка
        Menu menu = new Menu();
        menu.setId(1L);
        WaiterAccount waiterAccount = new WaiterAccount(1L, "Name", null, "MALE", null);
        List<OrderPosition> orderPositions = new ArrayList<>(List.of(
                new OrderPosition(1L, 1L, null, menu)
        ));
        WaiterOrder waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, waiterAccount, orderPositions);

        WaiterOrderResponse expected = new WaiterOrderResponse(OrderStatus.ACCEPTED,
                waiterOrder.getCreateDateTime(), "Vip-5", waiterAccount.getId(), Map.of(menu.getId(), 1L));

        // Действие
        WaiterOrderResponse actual = waiterOrderConverter.mapToWaiterOrderResponse(waiterOrder);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }
}
