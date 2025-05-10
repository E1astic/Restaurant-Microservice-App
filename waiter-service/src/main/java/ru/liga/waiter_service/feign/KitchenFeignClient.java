package ru.liga.waiter_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.waiter_service.models.dto.SimpleKitchenOrderResponse;

/**
 * Клиент Feign для взаимодействия с микросервисом кухни.
 * <p>
 * Этот интерфейс предоставляет методы для отправки запросов в микросервис кухни.
 * </p>
 */
@FeignClient(name = "kitchenFeignClient")
public interface KitchenFeignClient {

    /**
     * Отправляет заказ в микросервис кухни для последующей обработки - принятия или отклонения заказа.
     * <p>
     * Метод выполняет POST-запрос на эндпоинт "/orders" с объектом {@link KitchenOrderRequest}.
     * В случае успешного выполнения запроса возвращается объект {@link SimpleKitchenOrderResponse},
     * содержащий информацию о принятом заказе.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий данные заказа для кухни.
     * @return {@link ResponseEntity} с объектом {@link SimpleKitchenOrderResponse}, содержащим ответ от микросервиса.
     * Код ответа: 200 (OK) или другой HTTP-статус в зависимости от результата.
     */
    @PostMapping("/orders")
    ResponseEntity<SimpleKitchenOrderResponse> acceptOrder(KitchenOrderRequest kitchenOrderRequest);
}
