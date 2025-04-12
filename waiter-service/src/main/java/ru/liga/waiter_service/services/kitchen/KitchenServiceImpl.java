package ru.liga.waiter_service.services.kitchen;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.feign.KitchenFeignClient;
import ru.liga.waiter_service.models.dto.SimpleKitchenOrderResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;


@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenFeignClient kitchenFeignClient;
    private final WaiterOrderConverter waiterOrderConverter;

    public ResponseEntity<SimpleKitchenOrderResponse> sendOrderToKitchen(WaiterOrderRequest waiterOrderRequest) {
        return kitchenFeignClient.acceptOrder(waiterOrderConverter
                .mapToKitchenOrderRequest(waiterOrderRequest));
    }
}
