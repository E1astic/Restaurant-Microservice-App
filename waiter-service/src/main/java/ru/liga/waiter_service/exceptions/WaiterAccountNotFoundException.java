package ru.liga.waiter_service.exceptions;

/**
 * Исключение используется в случае, когда при попытке создания заказа
 * указывается ID несуществующего официанта.
 */
public class WaiterAccountNotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром, принимающий сообщение об ошибке и вызывающий
     * конструктор базового класса {@link RuntimeException}.
     *
     * @param message Строка с сообщением об ошибке
     */
    public WaiterAccountNotFoundException(String message) {
        super(message);
    }
}
