package ru.liga.waiter_service.utils;

public class MenuPositionNotFoundException extends RuntimeException {
    public MenuPositionNotFoundException(String message) {
        super(message);
    }
}
