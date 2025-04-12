package ru.liga.kitchen_service.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderToDish {

    private KitchenOrder kitchenOrder;

    private Dish dish;

    private Long dishesNumber;
}
