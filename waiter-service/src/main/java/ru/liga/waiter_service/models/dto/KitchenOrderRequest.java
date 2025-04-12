package ru.liga.waiter_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrderRequest {

    private Long waiterOrderNo;

    private Map<Long, Long> orderPositions;
}
