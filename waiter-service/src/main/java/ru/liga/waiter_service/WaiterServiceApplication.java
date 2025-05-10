package ru.liga.waiter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Главный класс приложения Waiter Service.
 *
 * <p>Этот класс является точкой входа для Spring Boot приложения. Он настраивает и запускает контекст Spring,
 * а также включает поддержку Feign-клиентов для взаимодействия с внешними сервисами.</p>
 */
@SpringBootApplication
@EnableFeignClients
public class WaiterServiceApplication {

    /**
     * Точка входа в приложение.
     *
     * <p>Запускает Spring Boot приложение, инициализируя контекст Spring и запуская все необходимые компоненты.
     * Метод принимает аргументы командной строки, которые могут быть использованы для настройки приложения.</p>
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(WaiterServiceApplication.class, args);
    }

}
