package ru.liga.waiter_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данное DTO используется для возвращения ID нового добавленного заказа.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleKitchenOrderResponse {

    private Long orderId;
}
