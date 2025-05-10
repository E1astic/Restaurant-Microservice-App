package ru.liga.common.exceptions;

/**
 * Исключение используется при попытке получить или обновить заказ
 * с несуществующим ID.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром, принимающий сообщение об ошибке и вызывающий
     * конструктор базового класса {@link RuntimeException}.
     *
     * @param message Строка с сообщением об ошибке
     */
    public OrderNotFoundException(String message) {
        super(message);
    }
}
