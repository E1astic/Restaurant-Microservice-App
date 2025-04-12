package ru.liga.kitchen_service.models.entity;

import lombok.Builder;
import lombok.Data;
import ru.liga.kitchen_service.utils.KitchenStatus;

import java.time.OffsetDateTime;

@Data
@Builder
public class KitchenOrder {

    private Long id;

    private Long waiterOrderNo;

    private KitchenStatus status;

    private OffsetDateTime createDateTime;
}
