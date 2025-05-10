package ru.liga.kitchen_service.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.liga.kitchen_service.converters.KitchenOrderConverter;
import ru.liga.kitchen_service.feign.WaiterFeignClient;
import ru.liga.kitchen_service.mappers.KitchenOrderMapper;
import ru.liga.kitchen_service.models.enums.KitchenStatus;
import ru.liga.kitchen_service.services.kitchen.KitchenServiceImpl;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishService;

import java.io.File;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(KitchenController.class)
@Import(KitchenServiceImpl.class)
public class KitchenControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KitchenOrderMapper kitchenOrderMapper;

    @MockitoBean
    private OrderToDishService orderToDishService;

    @MockitoBean
    private KitchenOrderConverter kitchenOrderConverter;

    @MockitoBean
    private WaiterFeignClient waiterFeignClient;

    @Test
    void shouldSetStatus() throws Exception {
        // Подготовка
        Long orderId = 1L;
        String status = "rejected";
        MockHttpServletRequestBuilder patch = MockMvcRequestBuilders.patch(String.format(
                "/orders/%d/status/%s", orderId, status));
        when(kitchenOrderMapper.setStatus(orderId, KitchenStatus.REJECTED)).thenReturn(1);
        doNothing().when(waiterFeignClient).setStatus(orderId, status);

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedOrderId = MockMvcResultMatchers.jsonPath("$.message")
                .value("Статус заказа был успешно обновлен");

        // Действие
        ResultActions actual = mockMvc.perform(patch);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedOrderId);
    }

    @Test
    void setStatus_shouldThrowExceptionIfOrderNotFound() throws Exception {
        // Подготовка
        Long orderId = 1L;
        String status = "rejected";
        MockHttpServletRequestBuilder patch = MockMvcRequestBuilders.patch(String.format(
                "/orders/%d/status/%s", orderId, status));
        when(kitchenOrderMapper.setStatus(orderId, KitchenStatus.REJECTED)).thenReturn(0);

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isNotFound();
        ResultMatcher expectedOrderId = MockMvcResultMatchers.jsonPath("$.errorMessage")
                .value(String.format("Заказа с id = %d не существует", orderId));

        // Действие
        ResultActions actual = mockMvc.perform(patch);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedOrderId);
    }

    @Test
    void setStatus_shouldThrowExceptionIfStatusIncorrect() throws Exception {
        // Подготовка
        Long orderId = 1L;
        String status = "reject";
        MockHttpServletRequestBuilder patch = MockMvcRequestBuilders.patch(String.format(
                "/orders/%d/status/%s", orderId, status));

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher expectedOrderId = MockMvcResultMatchers.jsonPath("$.errorMessage")
                .value("Некорректный статус заказа");

        // Действие
        ResultActions actual = mockMvc.perform(patch);

        // Проверка
        actual.andExpect(expectedStatus)
                .andExpect(expectedOrderId);
    }
}
