package ru.liga.kitchen_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.converters.KitchenOrderConverter;
import ru.liga.kitchen_service.exceptions.NotEnoughDishesException;
import ru.liga.kitchen_service.mappers.KitchenOrderMapper;
import ru.liga.kitchen_service.models.entity.Dish;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.models.enums.KitchenStatus;
import ru.liga.kitchen_service.services.dish.DishService;
import ru.liga.kitchen_service.services.kitchen.KitchenService;
import ru.liga.kitchen_service.services.order_processing.OrderProcessingServiceImpl;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderProcessingServiceImplTest {

    @Mock
    private KitchenService kitchenService;
    @Mock
    private DishService dishService;
    @Mock
    private KitchenOrderConverter kitchenOrderConverter;
    @Mock
    private KitchenOrderMapper kitchenOrderMapper;
    @Mock
    private OrderToDishService orderToDishService;

    @InjectMocks
    private OrderProcessingServiceImpl orderProcessingService;

    private KitchenOrderRequest kitchenOrderRequest;
    private Map<Long, Long> orderPositions;

    @BeforeEach
    void init() {
        orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        kitchenOrderRequest = new KitchenOrderRequest(1L, orderPositions);
    }

    @Test
    void shouldCheckDishNumberInStock() {
        // Подготовка
        List<Dish> dishesInStock = new ArrayList<>(List.of(
                new Dish(1L, 2L, "Dish1", null),
                new Dish(2L, 2L, "Dish2", null)
        ));
        when(dishService.getDishesById(new ArrayList<>(kitchenOrderRequest.getOrderPositions().keySet())))
                .thenReturn(dishesInStock);

        Map<Long, Long> expectedRemains = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 0L);
        }};

        // Действие
        Map<Long, Long> actualRemains = orderProcessingService.checkDishNumberInStock(kitchenOrderRequest);

        // Проверка
        Assertions.assertEquals(expectedRemains, actualRemains);
    }

    @Test
    void сheckDishNumberInStock_shouldThrowExceptionIfNotEnoughDishes() {
        // Подготовка
        List<Dish> dishesInStock = new ArrayList<>(List.of(
                new Dish(1L, 2L, "Dish1", null),
                new Dish(2L, 1L, "Dish2", null)
        ));
        when(dishService.getDishesById(new ArrayList<>(kitchenOrderRequest.getOrderPositions().keySet())))
                .thenReturn(dishesInStock);

        // Действие
        Executable executable = () -> orderProcessingService.checkDishNumberInStock(kitchenOrderRequest);

        // Проверка
        Assertions.assertThrows(NotEnoughDishesException.class, executable);
    }

    @Test
    void processOrder_shouldSetPreparingStatus() {
        // Проверка
        KitchenOrder kitchenOrder = new KitchenOrder(1L, 1L,
                KitchenStatus.RECEIVED, OffsetDateTime.now());

        when(kitchenService.receiveOrder(kitchenOrderRequest)).thenReturn(kitchenOrder);

        // вызов метода checkingDishNumberInStock(kitchenOrderRequest)
        List<Dish> dishesInStock = new ArrayList<>(List.of(
                new Dish(1L, 2L, "Dish1", null),
                new Dish(2L, 2L, "Dish2", null)
        ));
        when(dishService.getDishesById(new ArrayList<>(kitchenOrderRequest.getOrderPositions().keySet())))
                .thenReturn(dishesInStock);

        Map<Long, Long> dishIdAndRemains = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 0L);
        }};
        // вызов метода checkingDishNumberInStock(kitchenOrderRequest)

        doNothing().when(kitchenService).setStatus(kitchenOrder.getId(), KitchenStatus.PREPARING.toString());
        when(dishService.updateDishNums(new ArrayList<>(dishIdAndRemains.keySet()),
                new ArrayList<>(dishIdAndRemains.values()))).thenReturn(2);

        // Действие
        Long id = orderProcessingService.processOrder(kitchenOrderRequest);

        // Проверка
        Assertions.assertEquals(id, kitchenOrder.getId());
        verify(kitchenService, times(1))
                .setStatus(kitchenOrder.getId(), KitchenStatus.PREPARING.toString());
        verify(dishService, times(1)).updateDishNums(new ArrayList<>(dishIdAndRemains.keySet()),
                new ArrayList<>(dishIdAndRemains.values()));
    }

    @Test
    void processOrder_shouldSetRejectedStatus() {
        // Проверка
        KitchenOrder kitchenOrder = new KitchenOrder(1L, 1L,
                KitchenStatus.RECEIVED, OffsetDateTime.now());

        when(kitchenService.receiveOrder(kitchenOrderRequest)).thenReturn(kitchenOrder);

        // вызов метода checkingDishNumberInStock(kitchenOrderRequest)
        List<Dish> dishesInStock = new ArrayList<>(List.of(
                new Dish(1L, 2L, "Dish1", null),
                new Dish(2L, 1L, "Dish2", null)
        ));
        when(dishService.getDishesById(new ArrayList<>(kitchenOrderRequest.getOrderPositions().keySet())))
                .thenReturn(dishesInStock);
        // вызов метода checkingDishNumberInStock(kitchenOrderRequest)

        doNothing().when(kitchenService).setStatus(kitchenOrder.getId(), KitchenStatus.REJECTED.toString());

        // Действие
        Long id = orderProcessingService.processOrder(kitchenOrderRequest);

        // Проверка
        Assertions.assertEquals(id, kitchenOrder.getId());
        verify(kitchenService, times(1))
                .setStatus(kitchenOrder.getId(), KitchenStatus.REJECTED.toString());
        verify(kitchenService, times(0))
                .setStatus(kitchenOrder.getId(), KitchenStatus.PREPARING.toString());
        verify(dishService, times(0)).updateDishNums(any(), any());
    }
}
