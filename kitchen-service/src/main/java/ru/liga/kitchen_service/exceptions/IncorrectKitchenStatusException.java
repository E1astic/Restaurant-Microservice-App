package ru.liga.kitchen_service.exceptions;

/**
 * Исключение используется в случае, когда при изменении статуса заказа
 * передается некорректный или несуществующий статус.
 */
public class IncorrectKitchenStatusException extends RuntimeException {

    /**
     * Конструктор с параметром, принимающий сообщение об ошибке и вызывающий
     * конструктор базового класса {@link RuntimeException}.
     *
     * @param message Строка с сообщением об ошибке
     */
    public IncorrectKitchenStatusException(String message) {
        super(message);
    }
}
