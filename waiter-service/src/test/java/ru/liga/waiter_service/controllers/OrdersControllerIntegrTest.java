package ru.liga.waiter_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.WaiterOrderRepository;
import ru.liga.waiter_service.services.kitchen.KitchenService;
import ru.liga.waiter_service.services.order_position.OrderPositionService;
import ru.liga.waiter_service.services.waiter_account.WaiterAccountService;
import ru.liga.waiter_service.exceptions.MenuPositionNotFoundException;
import ru.liga.waiter_service.models.enums.OrderStatus;
import ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException;
import ru.liga.waiter_service.services.waiter_order.WaiterOrderServiceImpl;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(OrdersController.class)
@Import(WaiterOrderServiceImpl.class)
public class OrdersControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WaiterAccountService waiterAccountService;
    @MockitoBean
    private WaiterOrderConverter waiterOrderConverter;
    @MockitoBean
    private WaiterOrderRepository waiterOrderRepository;
    @MockitoBean
    private OrderPositionService orderPositionService;
    @MockitoBean
    private KitchenService kitchenService;

    @Test
    void shouldAddOrder() throws Exception {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/orders");
        post.contentType(MediaType.APPLICATION_JSON);
        post.content(objectMapper.writeValueAsString(waiterOrderRequest));

        WaiterAccount waiterAccount = new WaiterAccount(1L, "Name1", null, "MALE", null);
        when(waiterAccountService.getWaiterAccountById(1L)).thenReturn(waiterAccount);

        WaiterOrder waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, waiterAccount, null);
        when(waiterOrderConverter.mapToWaiterOrder(waiterOrderRequest, waiterAccount)).thenReturn(waiterOrder);

        when(waiterOrderRepository.save(waiterOrder)).thenReturn(waiterOrder);
        doNothing().when(orderPositionService).addOrderPositions(waiterOrder, waiterOrderRequest);
        doNothing().when(kitchenService).sendOrderToKitchen(waiterOrderRequest, waiterOrder);


        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedOrderId = MockMvcResultMatchers.jsonPath("$.orderId").value(waiterOrder.getId());

        // Действие
        ResultActions actual = mockMvc.perform(post);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedOrderId);
    }

    @Test
    void addOrder_shouldThrowExceptionIfWaiterNotFound() throws Exception {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/orders");
        post.contentType(MediaType.APPLICATION_JSON);
        post.content(objectMapper.writeValueAsString(waiterOrderRequest));

        when(waiterAccountService.getWaiterAccountById(1L)).thenThrow(new WaiterAccountNotFoundException(
                String.format("Официанта с id = %d не существует", waiterOrderRequest.getWaiterId())));

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher expectedErrorMessage = MockMvcResultMatchers.jsonPath("$.errorMessage")
                .value(String.format("Официанта с id = %d не существует", waiterOrderRequest.getWaiterId()));

        // Действие
        ResultActions actual = mockMvc.perform(post);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedErrorMessage);
    }

    @Test
    void addOrder_shouldThrowExceptionIfOrderPositionsNotFound() throws Exception {
        // Подготовка
        Map<Long, Long> orderPositions = new HashMap<>() {{
            put(1L, 1L);
            put(2L, 2L);
        }};
        WaiterOrderRequest waiterOrderRequest = new WaiterOrderRequest(1L, "Vip-5", orderPositions);

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/orders");
        post.contentType(MediaType.APPLICATION_JSON);
        post.content(objectMapper.writeValueAsString(waiterOrderRequest));

        WaiterAccount waiterAccount = new WaiterAccount(1L, "Name1", null, "MALE", null);
        when(waiterAccountService.getWaiterAccountById(1L)).thenReturn(waiterAccount);

        WaiterOrder waiterOrder = new WaiterOrder(1L, OrderStatus.ACCEPTED, OffsetDateTime.now(),
                "Vip-5", null, waiterAccount, null);
        when(waiterOrderConverter.mapToWaiterOrder(waiterOrderRequest, waiterAccount)).thenReturn(waiterOrder);

        when(waiterOrderRepository.save(waiterOrder)).thenReturn(waiterOrder);
        doThrow(new MenuPositionNotFoundException("Позиции меню с id " + Set.of(1L, 2L) + " не найдены"))
                .when(orderPositionService).addOrderPositions(waiterOrder, waiterOrderRequest);


        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher expectedOrderId = MockMvcResultMatchers.jsonPath("$.errorMessage")
                .value("Позиции меню с id " + Set.of(1L, 2L) + " не найдены");

        // Действие
        ResultActions actual = mockMvc.perform(post);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedOrderId);
    }
}
