package ru.liga.waiter_service.exceptions;

/**
 * Исключение используется в случае, когда при попытке создания заказа
 * передаются несуществующие позиции меню.
 */
public class MenuPositionNotFoundException extends RuntimeException {

    /**
     * Конструктор с параметром, принимающий сообщение об ошибке и вызывающий
     * конструктор базового класса {@link RuntimeException}.
     *
     * @param message Строка с сообщением об ошибке
     */
    public MenuPositionNotFoundException(String message) {
        super(message);
    }
}
