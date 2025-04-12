package ru.liga.kitchen_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.utils.KitchenStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrderWithDishesResponse {

    private Long waiterOrderNo;

    private KitchenStatus status;

    private OffsetDateTime createDateTime;

    private List<DishAndQuantity> dishAndQuantityList;

}
