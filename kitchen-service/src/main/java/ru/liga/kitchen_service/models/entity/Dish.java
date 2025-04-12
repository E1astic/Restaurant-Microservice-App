package ru.liga.kitchen_service.models.entity;

import lombok.Data;

@Data
public class Dish {

    private Long id;

    private Long balance;

    private String shortName;

    private String dishComposition;
}
