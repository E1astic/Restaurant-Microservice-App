package ru.liga.waiter_service.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.waiter_service.models.enums.OrderStatus;

/**
 * Модель данных для представления статуса заказа по его ID.
 * <p>
 * Этот класс используется для передачи информации о статусе заказа от сервера к клиенту.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность получения статуса заказа по ID заказа")
public class WaiterOrderStatusResponse {

    @Schema(description = "Статус заказа", allowableValues = {"ACCEPTED", "REJECTED", "READY"})
    private OrderStatus orderStatus;
}
