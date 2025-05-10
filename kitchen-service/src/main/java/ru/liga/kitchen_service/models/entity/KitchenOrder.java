package ru.liga.kitchen_service.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrder {

    private Long id;

    private Long waiterOrderNo;

    private KitchenStatus status;

    private OffsetDateTime createDateTime;
}
