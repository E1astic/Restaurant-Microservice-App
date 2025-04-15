package ru.liga.waiter_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.order-topic}")
    private String topicName;

    @Value("${spring.kafka.producer.partitions-num}")
    private int partitionsNum;

    @Value("${spring.kafka.producer.replicas-num}")
    private int replicasNum;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder
                .name(topicName)
                .partitions(partitionsNum)
                .replicas(replicasNum)
                .build();
    }
}
