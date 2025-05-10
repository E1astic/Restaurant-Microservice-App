package ru.liga.waiter_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Класс конфигурации для Kafka.
 *
 * <p>Этот класс настраивает топик Kafka, включая его имя, количество партиций и реплик.
 * Конфигурация топика загружается из свойств приложения application.yml
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.order-topic}")
    private String topicName;

    @Value("${spring.kafka.producer.partitions-num}")
    private int partitionsNum;

    @Value("${spring.kafka.producer.replicas-num}")
    private int replicasNum;

    /**
     * Создает и настраивает новый топик Kafka.
     *
     * <p>Метод создает экземпляр {@link NewTopic} с заданными параметрами:
     * именем топика, количеством партиций и реплик. Этот топик будет автоматически создан
     * при запуске приложения, если он еще не существует.</p>
     *
     * @return настроенный топик Kafka в виде объекта {@link NewTopic}.
     */
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
                .name(topicName)
                .partitions(partitionsNum)
                .replicas(replicasNum)
                .build();
    }
}
