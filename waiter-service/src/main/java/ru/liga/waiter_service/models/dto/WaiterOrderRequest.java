package ru.liga.waiter_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderRequest {
    private Long waiterId;
    private String tableNo;
    private Map<Long, Long> orderPositions;
}
