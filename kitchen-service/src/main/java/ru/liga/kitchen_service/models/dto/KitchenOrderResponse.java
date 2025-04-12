package ru.liga.kitchen_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.utils.KitchenStatus;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrderResponse {

    private Long waiterOrderNo;

    private KitchenStatus status;

    private OffsetDateTime createDateTime;
}
