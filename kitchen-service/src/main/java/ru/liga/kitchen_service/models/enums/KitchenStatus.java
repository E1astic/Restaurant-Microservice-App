package ru.liga.kitchen_service.models.enums;

/**
 * Перечисление, представляющее статусы заказа кухни.
 * <p>
 * Используется для отслеживания текущего состояния заказа в системе. Возможные значения:
 * <ul>
 *     <li>{@link #PREPARING} - заказ принят и находится в процессе приготовления.</li>
 *     <li>{@link #REJECTED} - заказ отменен.</li>
 *     <li>{@link #READY} - заказ готов.</li>
 * </ul>
 * </p>
 */
public enum KitchenStatus {

    RECEIVED,

    PREPARING,

    REJECTED,

    READY
}
