package ru.liga.waiter_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.OrderPositionRepository;
import ru.liga.waiter_service.services.menu.MenuService;
import ru.liga.waiter_service.services.order_position.OrderPositionServiceImpl;
import ru.liga.waiter_service.exceptions.MenuPositionNotFoundException;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderPositionServiceImplTest {

    @Mock
    private OrderPositionRepository orderPositionRepository;
    @Mock
    private MenuService menuService;

    @InjectMocks
    private OrderPositionServiceImpl orderPositionService;

    private WaiterOrder waiterOrder;
    private WaiterOrderRequest waiterOrderRequest;
    private Map<Long, Long> orderPositions;

    @BeforeEach
    public void init() {
        waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, null, null);
        orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);
    }

    @Test
    void shouldCheckExistenceMenuPositions() {
        // Подготовка
        Set<Long> dishIdSet = new HashSet<>(orderPositions.keySet());

        // Действие
        Executable executable = () -> orderPositionService.checkExistenceMenuPositions(orderPositions, dishIdSet);

        // Проверка
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    void checkExistenceMenuPositions_shouldThrowExceptionIfMenuPositionNotFound() {
        // Подготовка
        Set<Long> dishIdSet = new HashSet<>(Set.of(2L));

        // Действие
        Executable executable = () -> orderPositionService.checkExistenceMenuPositions(orderPositions, dishIdSet);

        // Проверка
        Assertions.assertThrows(MenuPositionNotFoundException.class, executable);
    }

    @Test
    void shouldCreateOrderPositions() {
        // Подготовка
        List<Menu> menuList = new ArrayList<>(List.of(
                new Menu(1L, "Dish1", BigDecimal.valueOf(500), null),
                new Menu(2L, "Dish2", BigDecimal.valueOf(1000), null)));

        List<OrderPosition> expected = new ArrayList<>(List.of(
                new OrderPosition(1L, 1L, waiterOrder, menuList.get(0)),
                new OrderPosition(2L, 2L, waiterOrder, menuList.get(1))
        ));

        // Действие
        List<OrderPosition> actual = orderPositionService.createOrderPositions(menuList, orderPositions, waiterOrder);
        actual.get(0).setId(1L);
        actual.get(1).setId(2L);
        System.out.println(actual.get(0).equals(expected.get(0)));
        System.out.println(actual.get(1).equals(expected.get(1)));

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void shouldAddOrderPositions() {
        // Подготовка
        List<Menu> menuList = new ArrayList<>(List.of(
                new Menu(1L, "Dish1", BigDecimal.valueOf(500), null),
                new Menu(2L, "Dish2", BigDecimal.valueOf(1000), null)));
        when(menuService.getMenuById(Arrays.asList(1L, 2L))).thenReturn(menuList);

        // Действие
        orderPositionService.addOrderPositions(waiterOrder, waiterOrderRequest);

        // Проверка
        verify(orderPositionRepository, times(1)).saveAll(anyList());
        verify(menuService, times(1)).getMenuById(Arrays.asList(1L, 2L));
    }

    @Test
    void shouldThrowExceptionIfSomeMenuNotFound() {
        // Подготовка
        List<Menu> menuList = new ArrayList<>(List.of(
                new Menu(2L, "Dish2", BigDecimal.valueOf(1000), null)));
        when(menuService.getMenuById(Arrays.asList(1L, 2L))).thenReturn(menuList);

        // Действие
        Executable executable = () -> orderPositionService.addOrderPositions(waiterOrder, waiterOrderRequest);

        // Проверка
        Assertions.assertThrows(MenuPositionNotFoundException.class, executable);
        verify(menuService, times(1)).getMenuById(Arrays.asList(1L, 2L));
        verify(orderPositionRepository, times(0)).saveAll(anyList());
    }
}
