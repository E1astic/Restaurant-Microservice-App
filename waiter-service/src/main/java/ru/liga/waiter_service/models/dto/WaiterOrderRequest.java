package ru.liga.waiter_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Модель данных для добавления нового заказа официанта.
 * <p>
 * Этот класс используется для передачи данных о новом заказе от клиента к серверу.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность добавления нового заказа официанта")
public class WaiterOrderRequest {

    @Schema(description = "ID официанта, который принимает заказ", example = "10")
    private Long waiterId;

    @Schema(description = "Название столика, за которым был принят заказ", example = "Vip-5")
    private String tableNo;

    @Schema(description = "Заказанные позиции из меню с их количеством (хранятся в Map<Long, Long>). "
            + "Ключ - ID блюда в меню, значение - количество данного блюда в заказе",
            example = "{\"1\": 3, \"2\": 5}")
    private Map<Long, Long> orderPositions;
}
