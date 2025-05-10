package ru.liga.kitchen_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.time.OffsetDateTime;

/**
 * Класс, представляющий ответ с информацией о заказе кухни.
 * <p>
 * Содержит данные о заказе кухни, такие как номер заказа официанта, статус заказа и дата его создания.
 * Используется для передачи информации о заказах между слоями приложения или в API-ответах.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность получения заказа кухни")
public class KitchenOrderResponse {

    @Schema(description = "ID заказа официанта", example = "3")
    private Long waiterOrderNo;

    @Schema(description = "Статус заказа кухни", allowableValues = {"PREPARING", "REJECTED", "READY"})
    private KitchenStatus status;

    @Schema(description = "Дата создания заказа официантом")
    private OffsetDateTime createDateTime;
}
