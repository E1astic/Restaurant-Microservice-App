package ru.liga.waiter_service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.liga.common.dto.KitchenOrderRequest;

/**
 * Компонент для отправки заказов официантов в Kafka.
 * <p>
 * Класс используется для публикации заказов, созданных официантами, в Kafka-топик. Это позволяет передавать заказы
 * на обработку в другие микросервисы (например, на кухню). Основной метод {@link #produce(KitchenOrderRequest)}
 * выполняет отправку данных в Kafka.
 * </p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderProducer {

    @Value("${spring.kafka.producer.order-topic}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Отправляет заказ официанта в Kafka-топик.
     * <p>
     * Метод выполняет отправку объекта {@link KitchenOrderRequest} в Kafka-топик, заданный в {@link #topicName}.
     * После отправки логируется информация о номере отправленного заказа.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию о заказе официанта.
     *                            Не должен быть {@code null}.
     */
    public void produce(KitchenOrderRequest kitchenOrderRequest) {
        kafkaTemplate.send(topicName, kitchenOrderRequest);
        log.info("Официантами отправлен заказ № {} на кухню", kitchenOrderRequest.getWaiterOrderNo());
    }
}
