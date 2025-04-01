package ru.liga.kitchen_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.kitchen_service.utils.KitchenStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitchenOrder {
    private String dish;
    private String client;
    private KitchenStatus status;

}
