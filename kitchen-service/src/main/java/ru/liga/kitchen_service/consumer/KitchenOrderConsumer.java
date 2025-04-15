package ru.liga.kitchen_service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.services.kitchen_service.KitchenService;


@Component
@RequiredArgsConstructor
@Slf4j
public class KitchenOrderConsumer {

    private final KitchenService kitchenService;

    @KafkaListener(topics = "${spring.kafka.producer.order-topic}")
    public void consume(KitchenOrderRequest kitchenOrderRequest) {
        Long id = kitchenService.receiveOrder(kitchenOrderRequest);
        log.info("Заказ с № {} был получен кухней", id);
    }
}
