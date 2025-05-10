package ru.liga.kitchen_service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.services.order_processing.OrderProcessingService;


/**
 * Компонент для обработки заказов, полученных кухней через Kafka.
 * <p>
 * Класс является частью микросервиса кухни и используется для прослушивания Kafka-топика с заказами,
 * отправленными официантами. При получении заказа вызывается сервис {@link OrderProcessingService}
 * для дальнейшей обработки данных.
 * </p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KitchenOrderConsumer {

    private final OrderProcessingService orderProcessingService;

    /**
     * Прослушивает Kafka-топик и обрабатывает полученные заказы.
     * <p>
     * Метод автоматически вызывается при получении нового сообщения из Kafka-топика, указанного в конфигурации
     * ({@code spring.kafka.producer.order-topic}). Заказ передается в сервис {@link OrderProcessingService}
     * для дальнейшей обработки. Логируется информация о полученном заказе.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию о заказе, полученного от официантов.
     *                             Не должен быть {@code null}.
     */
    @KafkaListener(topics = "${spring.kafka.producer.order-topic}")
    public void consume(KitchenOrderRequest kitchenOrderRequest) {
        log.info("Заказ с № {} был получен кухней", kitchenOrderRequest.getWaiterOrderNo());
        orderProcessingService.processOrder(kitchenOrderRequest);
    }
}
