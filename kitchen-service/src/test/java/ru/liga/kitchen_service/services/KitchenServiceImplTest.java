package ru.liga.kitchen_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.converters.KitchenOrderConverter;
import ru.liga.kitchen_service.feign.WaiterFeignClient;
import ru.liga.kitchen_service.mappers.KitchenOrderMapper;
import ru.liga.kitchen_service.models.dto.DishAndQuantity;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.Dish;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.dish.DishService;
import ru.liga.kitchen_service.services.kitchen.KitchenServiceImpl;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishService;
import ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException;
import ru.liga.kitchen_service.models.enums.KitchenStatus;
import ru.liga.common.exceptions.OrderNotFoundException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KitchenServiceImplTest {

    @Mock
    private KitchenOrderMapper kitchenOrderMapper;
    @Mock
    private KitchenOrderConverter kitchenOrderConverter;
    @Mock
    private OrderToDishService orderToDishService;
    @Mock
    private WaiterFeignClient waiterFeignClient;

    @InjectMocks
    private KitchenServiceImpl kitchenService;

    @Test
    void shouldGetAllOrders() {
        // Подготовка
        List<KitchenOrder> kitchenOrders = new ArrayList<>(List.of(
                new KitchenOrder(1L, 1L, KitchenStatus.PREPARING, OffsetDateTime.now())
        ));
        List<KitchenOrderResponse> expected = new ArrayList<>(List.of(
                new KitchenOrderResponse(1L, KitchenStatus.PREPARING,
                        kitchenOrders.get(0).getCreateDateTime())
        ));

        when(kitchenOrderMapper.findAllKitchenOrders()).thenReturn(kitchenOrders);
        for (int i = 0; i < kitchenOrders.size(); ++i) {
            when(kitchenOrderConverter.mapToKitchenOrderResponse(kitchenOrders.get(i))).thenReturn(expected.get(i));
        }

        // Действие
        List<KitchenOrderResponse> actual = kitchenService.getAllOrders();

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void shouldGetAllOrdersWithDishes() {
        // Подготовка
        List<DishAndQuantity> dishes = new ArrayList<>(List.of(
                new DishAndQuantity(1L, 1L)
        ));
        List<KitchenOrderWithDishesResponse> expected = new ArrayList<>(List.of(
                new KitchenOrderWithDishesResponse(1L, KitchenStatus.PREPARING,
                        OffsetDateTime.now(), dishes)
        ));
        when(kitchenOrderMapper.findAllKitchenOrdersWithDishes()).thenReturn(expected);

        // Действие
        List<KitchenOrderWithDishesResponse> actual = kitchenService.getAllOrdersWithDishes();

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void shouldAcceptOrder() {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        KitchenOrderRequest kitchenOrderRequest = new KitchenOrderRequest(1L, orderPositions);
        KitchenOrder kitchenOrder = new KitchenOrder(1L, 1L,
                KitchenStatus.RECEIVED, OffsetDateTime.now());
        when(kitchenOrderConverter.mapToKitchenOrder(kitchenOrderRequest)).thenReturn(kitchenOrder);
        when(kitchenOrderMapper.save(kitchenOrder)).thenReturn(kitchenOrder.getId());
        doNothing().when(orderToDishService).addOrderToDishes(kitchenOrder, kitchenOrderRequest);

        // Действие
        KitchenOrder actual = kitchenService.receiveOrder(kitchenOrderRequest);

        // Проверка
        Assertions.assertEquals(kitchenOrder, actual);
        verify(kitchenOrderConverter, times(1)).mapToKitchenOrder(kitchenOrderRequest);
        verify(kitchenOrderMapper, times(1)).save(kitchenOrder);
        verify(orderToDishService, times(1)).addOrderToDishes(kitchenOrder, kitchenOrderRequest);
    }

    @Test
    void shouldSetStatus() {
        // Подготовка
        when(kitchenOrderMapper.setStatus(1L, KitchenStatus.REJECTED)).thenReturn(1);
        doNothing().when(waiterFeignClient).setStatus(1L, KitchenStatus.REJECTED.toString());

        // Действие
        kitchenService.setStatus(1L, "rejected");

        // Проверка
        verify(waiterFeignClient, times(1)).setStatus(1L, KitchenStatus.REJECTED.toString());
        verify(kitchenOrderMapper, times(1)).setStatus(1L, KitchenStatus.REJECTED);
    }

    @Test
    void setStatus_shouldThrowExceptionIfOrderNotFound() {
        // Подготовка
        when(kitchenOrderMapper.setStatus(1L, KitchenStatus.REJECTED)).thenReturn(0);

        // Действие
        Executable executable = () -> kitchenService.setStatus(1L, "rejected");

        // Проверка
        Assertions.assertThrows(OrderNotFoundException.class, executable);
        verify(waiterFeignClient, times(0)).setStatus(1L, KitchenStatus.REJECTED.toString());
        verify(kitchenOrderMapper, times(1)).setStatus(1L, KitchenStatus.REJECTED);
    }

    @Test
    void setStatus_shouldThrowExceptionIfStatusIncorrect() {
        // Подготовка

        // Действие
        Executable executable = () -> kitchenService.setStatus(1L, "reject");

        // Проверка
        Assertions.assertThrows(IncorrectKitchenStatusException.class, executable);
        verify(waiterFeignClient, times(0)).setStatus(1L, KitchenStatus.REJECTED.toString());
        verify(kitchenOrderMapper, times(0)).setStatus(1L, KitchenStatus.REJECTED);
    }
}
