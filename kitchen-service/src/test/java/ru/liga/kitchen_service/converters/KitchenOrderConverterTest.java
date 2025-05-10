package ru.liga.kitchen_service.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class KitchenOrderConverterTest {

    private KitchenOrderConverter kitchenOrderConverter;

    @BeforeEach
    void init() {
        kitchenOrderConverter = Mappers.getMapper(KitchenOrderConverter.class);
    }

    @Test
    void shouldMapToKitchenOrder() {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>(){{
            put(1L, 1L); put(2L, 2L);
        }};
        KitchenOrderRequest kitchenOrderRequest = new KitchenOrderRequest(1L, orderPositions);
        KitchenOrder expected = new KitchenOrder(1L, 1L,
                KitchenStatus.RECEIVED, OffsetDateTime.now());

        // Действие
        KitchenOrder actual = kitchenOrderConverter.mapToKitchenOrder(kitchenOrderRequest);
        actual.setId(expected.getId());
        actual.setCreateDateTime(expected.getCreateDateTime());

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldMapToKitchenOrderResponse() {
        // Подготовка
        KitchenOrder kitchenOrder = new KitchenOrder(1L, 1L,
                KitchenStatus.RECEIVED, OffsetDateTime.now());
        KitchenOrderResponse expected = new KitchenOrderResponse(1L,
                KitchenStatus.RECEIVED, kitchenOrder.getCreateDateTime());

        // Действие
        KitchenOrderResponse actual = kitchenOrderConverter.mapToKitchenOrderResponse(kitchenOrder);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }
}
