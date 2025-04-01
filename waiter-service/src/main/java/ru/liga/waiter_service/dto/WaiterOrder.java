package ru.liga.waiter_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.waiter_service.utils.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaiterOrder {
    private String dish;
    private String client;
    private OrderStatus status;
}
