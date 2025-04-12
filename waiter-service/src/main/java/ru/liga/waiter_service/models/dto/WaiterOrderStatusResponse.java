package ru.liga.waiter_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.waiter_service.utils.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderStatusResponse {

    private OrderStatus orderStatus;
}
