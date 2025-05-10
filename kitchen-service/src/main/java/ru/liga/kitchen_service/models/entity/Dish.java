package ru.liga.kitchen_service.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    private Long id;

    private Long balance;

    private String shortName;

    private String dishComposition;
}
