package ru.liga.waiter_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данное DTO используется для возвращения ID нового добавленного заказа.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность получения ID нового заказа")
public class SimpleWaiterOrderResponse {

    @Schema(description = "ID добавленного заказа", example = "10")
    private Long orderId;
}
