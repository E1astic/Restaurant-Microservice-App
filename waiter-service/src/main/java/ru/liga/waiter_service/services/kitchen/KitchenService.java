package ru.liga.waiter_service.services.kitchen;


import org.springframework.http.ResponseEntity;
import ru.liga.waiter_service.models.dto.SimpleKitchenOrderResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;

public interface KitchenService {

    ResponseEntity<SimpleKitchenOrderResponse> sendOrderToKitchen(WaiterOrderRequest waiterOrderRequest);
}
