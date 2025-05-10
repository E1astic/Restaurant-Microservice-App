package ru.liga.kitchen_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Класс, представляющий ответ с информацией о заказе кухни и списком блюд в заказе.
 * <p>
 * Содержит данные о заказе кухни, включая номер заказа официанта, статус заказа, дату создания
 * и список блюд с их количеством. Используется для передачи информации о заказах между слоями приложения
 * или в API-ответах.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность получения заказа кухни со списком блюд в заказе")
public class KitchenOrderWithDishesResponse {

    @Schema(description = "ID заказа официанта", example = "3")
    private Long waiterOrderNo;

    @Schema(description = "Статус заказа кухни", allowableValues = {"PREPARING", "REJECTED", "READY"})
    private KitchenStatus status;

    @Schema(description = "Дата создания заказа официантом")
    private OffsetDateTime createDateTime;

    @Schema(description = "Список блюд в заказе, содержащий ID блюда в заказе и его количество",
            implementation = DishAndQuantity.class)
    private List<DishAndQuantity> dishAndQuantityList;
}
