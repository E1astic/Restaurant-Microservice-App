package ru.liga.kitchen_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий пару "ID блюда и его количество" в заказе.
 * <p>
 * Содержит информацию об идентификаторе блюда и его количестве в заказе. Используется для работы с данными
 * о составе заказов в системе.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность, содержащая пару - ID блюда в заказе и его количество")
public class DishAndQuantity {

    @Schema(description = "ID блюда в заказе", example = "2")
    private Long id;

    @Schema(description = "Количество блюда в заказе", example = "5")
    private Long quantity;
}
