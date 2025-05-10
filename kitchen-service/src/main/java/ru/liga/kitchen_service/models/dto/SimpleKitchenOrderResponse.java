package ru.liga.kitchen_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий ответ с информацией об ID нового заказа кухни.
 * <p>
 * Содержит уникальный идентификатор добавленного заказа. Используется для передачи информации о созданном заказе
 * между слоями приложения или в API-ответах.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность получения ID нового заказа")
public class SimpleKitchenOrderResponse {

    @Schema(description = "ID добавленного заказа", example = "10")
    private Long orderId;
}
