package ru.liga.waiter_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.WaiterOrderRepository;
import ru.liga.waiter_service.services.kitchen.KitchenService;
import ru.liga.waiter_service.services.order_position.OrderPositionService;
import ru.liga.waiter_service.services.waiter_account.WaiterAccountService;
import ru.liga.waiter_service.services.waiter_order.WaiterOrderServiceImpl;
import ru.liga.waiter_service.exceptions.IncorrectOrderStatusException;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WaiterOrderServiceImplTest {

    @Mock
    private WaiterOrderConverter waiterOrderConverter;
    @Mock
    private WaiterOrderRepository waiterOrderRepository;
    @Mock
    private WaiterAccountService waiterAccountService;
    @Mock
    private OrderPositionService orderPositionService;
    @Mock
    private KitchenService kitchenService;

    @InjectMocks
    private WaiterOrderServiceImpl waiterOrderService;

    private List<WaiterOrder> waiterOrders;
    private List<WaiterOrderResponse> waiterOrderResponses;

    @BeforeEach
    void init(){
        waiterOrders = new ArrayList<>(List.of(
                new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                        "Vip-5", null, null, null),
                new WaiterOrder(2L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                        "Vip-10", null, null, null)));

        Map<Long, Long> orderPositions1 = new HashMap<>(){{
           put(1L, 1L); put(2L, 2L);
        }};
        Map<Long, Long> orderPositions2 = new HashMap<>(){{
            put(3L, 3L); put(4L, 4L);
        }};

        waiterOrderResponses = new ArrayList<>(List.of(
                new WaiterOrderResponse(OrderStatus.ACCEPTED, OffsetDateTime.now(),
                        "Vip-5", 1L, orderPositions1),
                new WaiterOrderResponse(OrderStatus.ACCEPTED, OffsetDateTime.now(),
                        "Vip-10", 2L, orderPositions2)
        ));
    }

    @Test
    void shouldGetAllOrders(){
        // Подготовка
        when(waiterOrderRepository.findAll()).thenReturn(waiterOrders);
        for(int i = 0; i < waiterOrders.size(); ++i){
            when(waiterOrderConverter.mapToWaiterOrderResponse(waiterOrders.get(i))).thenReturn(waiterOrderResponses.get(i));
        }

        // Действие
        List<WaiterOrderResponse> actual = waiterOrderService.getAllOrders();

        // Проверка
        Assertions.assertIterableEquals(waiterOrderResponses, actual);
    }

    @Test
    void shouldGetOrderById(){
        // Подготовка
        when(waiterOrderRepository.findById(1L)).thenReturn(Optional.ofNullable(waiterOrders.get(0)));
        when(waiterOrderConverter.mapToWaiterOrderResponse(waiterOrders.get(0))).thenReturn(waiterOrderResponses.get(0));

        // Действие
        WaiterOrderResponse actual = waiterOrderService.getOrderById(1L);

        // Проверка
        Assertions.assertEquals(waiterOrderResponses.get(0), actual);
    }

    @Test
    void getOrderById_shouldThrowExceptionIfOrderNotFound(){
        // Подготовка
        when(waiterOrderRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> waiterOrderService.getOrderById(1L);

        // Проверка
        Assertions.assertThrows(OrderNotFoundException.class, executable);
    }

    @Test
    void shouldGetOrderStatusById(){
        // Подготовка
        when(waiterOrderRepository.findById(1L)).thenReturn(Optional.ofNullable(waiterOrders.get(0)));
        when(waiterOrderConverter.mapToWaiterOrderResponse(waiterOrders.get(0))).thenReturn(waiterOrderResponses.get(0));

        // Действие
        OrderStatus actual = waiterOrderService.getOrderStatusById(1L);

        // Проверка
        Assertions.assertEquals(OrderStatus.ACCEPTED, actual);
    }

    @Test
    void shouldAddOrderPosition(){
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>(){{
            put(1L, 1L); put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);

        WaiterAccount waiterAccount = new WaiterAccount(1L, "Name", OffsetDateTime.now(), "MALE", null);
        when(waiterAccountService.getWaiterAccountById(1L)).thenReturn(waiterAccount);

        WaiterOrder waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, waiterAccount, null);
        when(waiterOrderConverter.mapToWaiterOrder(waiterOrderRequest, waiterAccount)).thenReturn(waiterOrder);

        when(waiterOrderRepository.save(waiterOrder)).thenReturn(waiterOrder);

        doNothing().when(orderPositionService).addOrderPositions(waiterOrder, waiterOrderRequest);
        doNothing().when(kitchenService).sendOrderToKitchen(waiterOrderRequest, waiterOrder);

        // Действие
        WaiterOrder actual = waiterOrderService.addOrder(waiterOrderRequest);

        // Проверка
        Assertions.assertEquals(waiterOrder, actual);
        verify(waiterAccountService, times(1)).getWaiterAccountById(waiterOrderRequest.getWaiterId());
        verify(waiterOrderConverter, times(1)).mapToWaiterOrder(waiterOrderRequest, waiterAccount);
        verify(waiterOrderRepository, times(1)).save(waiterOrder);
        verify(orderPositionService, times(1)).addOrderPositions(waiterOrder, waiterOrderRequest);
        verify(kitchenService, times(1)).sendOrderToKitchen(waiterOrderRequest, waiterOrder);
    }

    @Test
    void shouldUpdateOrderStatus(){
        // Подготовка
        when(waiterOrderRepository.updateStatus(1L, OrderStatus.REJECTED)).thenReturn(1);

        // Действие
        waiterOrderService.setStatus(1L, "rejected");

        // Проверка
        verify(waiterOrderRepository, times(1)).updateStatus(1L, OrderStatus.REJECTED);
    }

    @Test
    void updateOrderStatus_shouldThrowExceptionIfOrderNotFound(){
        // Подготовка
        when(waiterOrderRepository.updateStatus(1L, OrderStatus.REJECTED)).thenReturn(0);

        // Действие
        Executable executable = () -> waiterOrderService.setStatus(1L, "rejected");

        // Проверка
        Assertions.assertThrows(OrderNotFoundException.class, executable);
        verify(waiterOrderRepository, times(1)).updateStatus(1L, OrderStatus.REJECTED);
    }

    @Test
    void updateOrderStatus_shouldThrowExceptionIfStatusIncorrect(){
        // Подготовка

        // Действие
        Executable executable = () -> waiterOrderService.setStatus(1L, "reject");

        // Проверка
        Assertions.assertThrows(IncorrectOrderStatusException.class, executable);
        verify(waiterOrderRepository, times(0)).updateStatus(1L, OrderStatus.REJECTED);
    }

}
