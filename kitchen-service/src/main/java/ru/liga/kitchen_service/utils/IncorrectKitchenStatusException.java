package ru.liga.kitchen_service.utils;

public class IncorrectKitchenStatusException extends RuntimeException {
    public IncorrectKitchenStatusException(String message) {
        super(message);
    }
}
