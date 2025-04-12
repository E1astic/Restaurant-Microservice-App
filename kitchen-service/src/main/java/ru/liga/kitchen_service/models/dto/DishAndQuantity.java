package ru.liga.kitchen_service.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishAndQuantity {

    private Long id;

    private Long quantity;
}
