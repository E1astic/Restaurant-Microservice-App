package ru.liga.waiter_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Модель данных для представления информации о заказе официанта.
 * <p>
 * Этот класс используется для передачи данных о заказе от сервера к клиенту.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сущность получения заказа официанта")
public class WaiterOrderResponse {

    @Schema(description = "Статус заказа", allowableValues = {"ACCEPTED", "REJECTED", "READY"})
    private OrderStatus orderStatus;

    @Schema(description = "Дата и время создания заказа")
    private OffsetDateTime createDateTime;

    @Schema(description = "Номер столика, за которым был принят заказ", example = "Vip-5")
    private String tableNo;

    @Schema(description = "ID официанта, который принял заказ", example = "10")
    private Long waiterId;

    @Schema(description = "Заказанные позиции из меню с их количеством (хранятся в Map<Long, Long>). "
            + "Ключ - ID блюда в меню, значение - количество данного блюда в заказе",
            example = "{\"1\": 3, \"2\": 5}")
    private Map<Long, Long> orderPositions;
}
