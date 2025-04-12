package ru.liga.waiter_service.utils;

public class WaiterAccountNotFoundException extends RuntimeException {
    public WaiterAccountNotFoundException(String message) {
        super(message);
    }
}
