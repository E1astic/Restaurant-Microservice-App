package ru.liga.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Класс, представляющий запрос на добавление заказа кухни.
 * <p>
 * Содержит информацию о заказе, отправленном официантом на кухню, включая номер заказа официанта
 * и список заказанных позиций с их количеством. Используется для передачи данных между слоями приложения
 * или в API-запросах.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность добавления заказа кухни")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KitchenOrderRequest {

    @Schema(description = "ID заказа официанта", example = "10")
    @EqualsAndHashCode.Include
    private Long waiterOrderNo;

    @Schema(description = "Заказанные позиции из меню с их количеством (хранятся в Map<Long, Long>). "
            + "Ключ - ID блюда в меню, значение - количество данного блюда в заказе",
            example = "{\"1\": 3, \"2\": 5}")
    @EqualsAndHashCode.Include
    private Map<Long, Long> orderPositions;
}
