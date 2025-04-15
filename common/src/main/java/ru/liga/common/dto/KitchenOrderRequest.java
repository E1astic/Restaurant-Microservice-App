package ru.liga.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrderRequest {

    private Long waiterOrderNo;

    private Map<Long, Long> orderPositions;
}
