package ru.liga.kitchen_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Feign-клиент для взаимодействия с микросервисом официантов.
 * <p>
 * Этот интерфейс предоставляет методы для выполнения HTTP-запросов к микросервису официантов через REST API.
 * Используется для обновления статуса заказа в микросервисе официантов.
 * </p>
 */
@FeignClient(name = "waiterFeignClient")
public interface WaiterFeignClient {

    /**
     * Обновляет статус заказа в микросервисе официантов.
     * <p>
     * Метод выполняет PUT-запрос к эндпоинту микросервиса официантов для изменения статуса заказа.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Передается как путь в URL.
     * @param status новый статус заказа. Передается как путь в URL.
     */
    @PutMapping("/orders/{id}/status/{status}")
    void setStatus(@PathVariable("id") Long id, @PathVariable("status") String status);
}
