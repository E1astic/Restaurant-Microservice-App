package ru.liga.waiter_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.waiter_service.utils.OrderStatus;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaiterOrderResponse {
    private OrderStatus orderStatus;
    private OffsetDateTime createDateTime;
    private String tableNo;
    private Long waiterId;
    private Map<Long, Long> orderPositions;
}
