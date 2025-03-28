package ru.liga.kitchen_service.utils;

public enum KitchenStatus {
    AWAITING,    // ожидает начала работы
    REJECTED,    // отменен
    PREPARING,   // принят и готовится
    READY        // готов
}
