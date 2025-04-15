package ru.liga.waiter_service.services.kitchen;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.producer.WaiterOrderProducer;


@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final WaiterOrderProducer waiterOrderProducer;
    private final WaiterOrderConverter waiterOrderConverter;

    public void sendOrderToKitchen(WaiterOrderRequest waiterOrderRequest, WaiterOrder waiterOrder) {
        waiterOrderProducer.produce(waiterOrderConverter
                .mapToKitchenOrderRequest(waiterOrderRequest, waiterOrder.getId()));
    }
}
