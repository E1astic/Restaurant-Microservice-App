package ru.liga.kitchen_service.services.dish;

import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

public interface DishService {

    Dish getDishById(Long id);

    List<Dish> getDishesById(List<Long> id);
}
