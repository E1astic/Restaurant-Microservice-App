package ru.liga.waiter_service.utils;

public class IncorrectOrderStatusException extends RuntimeException {
    public IncorrectOrderStatusException(String message) {
        super(message);
    }
}
