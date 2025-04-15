package ru.liga.waiter_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.waiter_service.models.dto.SimpleKitchenOrderResponse;

@FeignClient(name = "kitchenFeignClient")
public interface KitchenFeignClient {

    @PostMapping("/orders")
    ResponseEntity<SimpleKitchenOrderResponse> acceptOrder(KitchenOrderRequest kitchenOrderRequest);
}
