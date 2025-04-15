package ru.liga.waiter_service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.liga.common.dto.KitchenOrderRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderProducer {

    @Value("${spring.kafka.producer.order-topic}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(KitchenOrderRequest kitchenOrderRequest) {
        kafkaTemplate.send(topicName, kitchenOrderRequest);
        log.info("Продюсером отправлен заказ № {}", kitchenOrderRequest.getWaiterOrderNo());
    }
}
